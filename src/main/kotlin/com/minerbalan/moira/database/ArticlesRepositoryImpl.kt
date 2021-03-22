package com.minerbalan.moira.database

import com.minerbalan.moira.database.rowmapper.ArticleRowMapper
import com.minerbalan.moira.domain.entity.Article
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
    override fun findByThumbnailIsNull(): List<Article> {
        val sql = "SELECT * FROM articles WHERE thumbnail IS NULL"
        return jdbcTemplate.query(sql, ArticleRowMapper())
    }

    /**
     * Articleを挿入
     */
    override fun bulkInsertOrIgnoreArticles(articleList: List<Article>) {
        if (articleList.isEmpty()) {
            return
        }
        //100件ずつinsertしていく
        val queue = ArrayDeque(articleList)
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
                            val parameterArticle: Article = queue.remove()
                            ps.setLong(++parameterPosition, parameterArticle.subscriptionId)
                            ps.setString(++parameterPosition, parameterArticle.url)
                            ps.setString(++parameterPosition, parameterArticle.title)
                            ps.setString(++parameterPosition, parameterArticle.description)
                            ps.setTimestamp(++parameterPosition, Timestamp.valueOf(parameterArticle.publishedAt))
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
    override fun bulkUpdateThumbnailArticles(articleList: List<Article>) {
        if (articleList.isEmpty()) {
            return
        }
        val queue = ArrayDeque(articleList)

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
                            val parameterArticle: Article = queue.remove()
                            val articleId = parameterArticle.id ?: throw RuntimeException("Article Id is Null")
                            // WHEN ... THEN... 句
                            ps.setLong(++parameterPosition, articleId)
                            ps.setString(++parameterPosition, parameterArticle.thumbnail)
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
}
