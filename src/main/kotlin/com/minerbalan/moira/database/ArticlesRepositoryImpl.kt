package com.minerbalan.moira.database

import com.minerbalan.moira.database.rowmapper.ArticleRowMapper
import com.minerbalan.moira.domain.entity.ArticleEntity
import com.minerbalan.moira.domain.repository.ArticlesRepository
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.lang.RuntimeException
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.util.*

@Repository
class ArticlesRepositoryImpl(private val jdbcTemplate: JdbcTemplate) : ArticlesRepository {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    private val maxBulkSize = 100

    /**
     * サムネイルがnullの項目を取得する
     */
    override fun findByThumbnailIsNull(): List<ArticleEntity> {
        val sql = "SELECT * FROM articles WHERE thumbnail IS NULL"
        return jdbcTemplate.query(sql, ArticleRowMapper())
    }

    /**
     * Articleを挿入
     */
    override fun bulkInsertOrIgnoreArticles(articleEntityList: List<ArticleEntity>) {
        if (articleEntityList.isEmpty()) {
            return
        }
        //100件ずつinsertしていく
        val queue = ArrayDeque(articleEntityList)
        try {
            while (!queue.isEmpty()) {
                val sqlBuilder = StringBuilder()
                sqlBuilder.append("INSERT IGNORE INTO articles")
                sqlBuilder.append("(subscription_id, url, title, description, published_at) ")
                sqlBuilder.append("VALUES ")
                val parameterSize = queue.size.coerceAtMost(maxBulkSize)
                val parameterList = Collections.nCopies(parameterSize, " (?,?,?,?,?) ")
                sqlBuilder.append(parameterList.joinToString(separator = ","))
                jdbcTemplate.update(sqlBuilder.toString()) { ps: PreparedStatement ->
                    run {
                        var parameterPosition = 0
                        for (j in 0 until parameterSize) {
                            val parameterArticleEntity: ArticleEntity = queue.remove()
                            ps.setLong(++parameterPosition, parameterArticleEntity.subscriptionId)
                            ps.setString(++parameterPosition, parameterArticleEntity.url)
                            ps.setString(++parameterPosition, parameterArticleEntity.title)
                            ps.setString(++parameterPosition, parameterArticleEntity.description)
                            ps.setTimestamp(++parameterPosition, Timestamp.valueOf(parameterArticleEntity.publishedAt))
                        }
                    }
                }
            }
        } catch (e: Exception) {
            logger.error("An error has occurred on insert article", e)
        }
    }

    /**
     * サムネイルをbulk update.
     */
    override fun bulkUpdateThumbnailArticles(articleEntityList: List<ArticleEntity>) {
        if (articleEntityList.isEmpty()) {
            return
        }
        val queue = ArrayDeque(articleEntityList)

        try {
            while (!queue.isEmpty()) {
                val sqlBuilder = StringBuilder()
                sqlBuilder.append("UPDATE articles SET ")
                sqlBuilder.append("thumbnail = ")
                sqlBuilder.append("CASE id ")
                val parameterSize = queue.size.coerceAtMost(maxBulkSize)
                val parameterWhenList = Collections.nCopies(parameterSize, " WHEN ? THEN ? ")
                sqlBuilder.append(parameterWhenList.joinToString(separator = " "))
                sqlBuilder.append(" END ")
                val parameterInList = Collections.nCopies(parameterSize, "?")
                sqlBuilder.append(" WHERE id IN ( ")
                sqlBuilder.append(parameterInList.joinToString(separator = ","))
                sqlBuilder.append(" ) ")
                jdbcTemplate.update(sqlBuilder.toString()) { ps: PreparedStatement ->
                    run {
                        var parameterPosition = 0
                        for (j in 0 until parameterSize) {
                            val parameterArticleEntity: ArticleEntity = queue.remove()
                            val articleId = parameterArticleEntity.id ?: throw RuntimeException("Article Id is Null")
                            // WHEN ... THEN... 句
                            ps.setLong(++parameterPosition, articleId)
                            ps.setString(++parameterPosition, parameterArticleEntity.thumbnail)
                            var space = parameterPosition + (parameterSize - j - 1) * 2 + j
                            ps.setLong(++space, articleId)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            logger.error("An error has occurred on update article thumbnail", e)
        }
    }

    override fun fetchArticleLatest(email: String, limit: Int, offset: Int): List<ArticleEntity> {
        val stringBuilder = StringBuilder()
        stringBuilder.appendln("SELECT articles.* FROM articles ")
        stringBuilder.appendln("    INNER JOIN users_subscriptions US ")
        stringBuilder.appendln("        ON articles.subscription_id = US.subscriptions_id ")
        stringBuilder.appendln("    INNER JOIN users ")
        stringBuilder.appendln("        ON US.users_id = users.id ")
        stringBuilder.appendln("            AND users.email = ? ")
        stringBuilder.appendln("ORDER BY published_at desc ")
        stringBuilder.appendln("LIMIT ? OFFSET ? ")

        return jdbcTemplate.query<ArticleEntity>(stringBuilder.toString(), ArticleRowMapper(), email, limit, offset)
    }

    override fun countArticle(email: String): Long {
        val stringBuilder = StringBuilder()
        stringBuilder.appendln("SELECT count(articles.id) as ARTICLES_COUNT FROM articles ")
        stringBuilder.appendln("    INNER JOIN users_subscriptions US ")
        stringBuilder.appendln("        ON articles.subscription_id = US.subscriptions_id ")
        stringBuilder.appendln("    INNER JOIN users ")
        stringBuilder.appendln("        ON US.users_id = users.id ")
        stringBuilder.appendln("            AND users.email = ? ")
        val sqlResult = jdbcTemplate.queryForMap(stringBuilder.toString(), email)
        val articleCount = sqlResult["ARTICLES_COUNT"]
        if(articleCount is Long){
            return articleCount
        }
        throw IllegalStateException("記事の取得中にエラー")
    }
}
