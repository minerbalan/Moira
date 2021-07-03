package com.minerbalan.moira.database

import com.minerbalan.moira.database.table.*
import com.minerbalan.moira.domain.entity.ArticleEntity
import com.minerbalan.moira.domain.repository.article.ArticleData
import com.minerbalan.moira.domain.repository.article.ArticlesRepository
import com.minerbalan.moira.domain.repository.article.InsertArticleData
import org.jetbrains.exposed.sql.*
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList

@Repository
class ArticlesRepositoryImpl(private val jdbcTemplate: JdbcTemplate) : ArticlesRepository {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    private val maxBulkSize = 100

    /**
     * サムネイルがnullの項目を取得する
     */
    override fun findByThumbnailIsNull(): List<ArticleEntity> {
        val result = ArrayList<ArticleEntity>()
        val query = ArticlesTable.select { ArticlesTable.thumbnail eq null }
        for (item in query) {
            result.add(item.toArticleEntity())
        }
        return result
    }

    /**
     * Articleを挿入
     */
    override fun bulkInsertOrIgnoreArticles(insertArticleList: List<InsertArticleData>) {
        if (insertArticleList.isEmpty()) {
            return
        }
        //100件ずつinsertしていく
        val queue = ArrayDeque(insertArticleList)
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
                            val parameterArticleData: InsertArticleData = queue.remove()
                            ps.setLong(++parameterPosition, parameterArticleData.subscriptionId)
                            ps.setString(++parameterPosition, parameterArticleData.url)
                            ps.setString(++parameterPosition, parameterArticleData.title)
                            ps.setString(++parameterPosition, parameterArticleData.description)
                            ps.setTimestamp(++parameterPosition, Timestamp.valueOf(parameterArticleData.publishedAt))
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
                            val articleId = parameterArticleEntity.id
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

    override fun fetchArticleLatest(email: String, limit: Int, offset: Int): List<ArticleData> {
        val result = ArrayList<ArticleData>()
        val resultRow = ArticlesTable
            .join(
                otherTable = UsersSubscriptionsTable,
                joinType = JoinType.INNER,
                onColumn = UsersSubscriptionsTable.subscriptionsId,
                otherColumn = ArticlesTable.subscriptionId
            )
            .join(
                otherTable = UsersTable,
                joinType = JoinType.INNER,
                onColumn = UsersTable.id,
                otherColumn = UsersSubscriptionsTable.usersId
            )
            .select { UsersTable.email eq email }
            .limit(limit, offset = offset.toLong())
            .orderBy(ArticlesTable.publishedAt, SortOrder.DESC)

        for (item in resultRow) {
            val articleEntity = item.toArticleEntity()
            val userSub = item.toUserSubscriptionEntity()
            val articleData = ArticleData(
                id = articleEntity.id,
                subscriptionId = articleEntity.subscriptionId,
                url = articleEntity.url,
                title = articleEntity.title,
                description = articleEntity.description,
                thumbnail = articleEntity.thumbnail,
                publishedAt = articleEntity.publishedAt,
                subscriptionName = userSub.name
            )
            result.add(articleData)
        }

        return result
    }

    override fun countArticle(email: String): Long {
        return ArticlesTable
            .join(
                otherTable = UsersSubscriptionsTable,
                joinType = JoinType.INNER,
                onColumn = UsersSubscriptionsTable.subscriptionsId,
                otherColumn = ArticlesTable.subscriptionId
            )
            .join(
                otherTable = UsersTable,
                joinType = JoinType.INNER,
                onColumn = UsersTable.id,
                otherColumn = UsersSubscriptionsTable.usersId
            )
            .select { UsersTable.email eq email }
            .count()
    }
}
