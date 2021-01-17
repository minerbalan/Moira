package com.minerbalan.moira.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
class ArticleService {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    companion object {
        private const val MAX_BULK_SIZE = 100
    }

    @PersistenceContext
    var entityManager: EntityManager? = null

    /**
     * ArticleテーブルにMysqlのInsertOrIgnoreを使ってinsertする.
     * 注意点として、MysqlのInsertOrIgnoreはカラム制約のNotNullやDefaultを無視するので、
     * Articleのsubscription_id,url,title,description,published_atはNullにしないようにする.
     * 100件ずつinsertしていく.
     *
     * @param articleList 挿入するArticleのリスト。nullもしくは空の場合は何も処理せずreturn.
     */
//    fun bulkInsertOrIgnoreArticles(articleList: List<Article>?) {
//        if (articleList == null || articleList.isEmpty()) {
//            return
//        }
//        //100件ずつinsertしていく
//        val queue = ArrayDeque(articleList)
//        try {
//            while (!queue.isEmpty()) {
//                val sql = StringBuilder()
//                sql.append("INSERT IGNORE INTO articles")
//                sql.append("(subscription_id, url, title, description, published_at) ")
//                sql.append("VALUES ")
//                val parameterSize = queue.size.coerceAtMost(MAX_BULK_SIZE)
//                val parameterList = Collections.nCopies(parameterSize, " (?,?,?,?,?) ")
//                sql.append(parameterList.joinToString(separator = ","))
//                val query = entityManager!!.createNativeQuery(sql.toString())
//                var parameterPosition = 0
//                for (j in 0 until parameterSize) {
//                    val parameterArticle: Article = queue.remove()
//                    query.setParameter(++parameterPosition, parameterArticle.subscriptionId)
//                    query.setParameter(++parameterPosition, parameterArticle.url)
//                    query.setParameter(++parameterPosition, parameterArticle.title)
//                    query.setParameter(++parameterPosition, parameterArticle.description)
//                    query.setParameter(++parameterPosition, parameterArticle.publishedAt)
//                }
//                query.executeUpdate()
//            }
//        } catch (e: Exception) {
//            logger.error("An error has occurred on insert article", e)
//        }
//    }

    /**
     * ArticleテーブルのArticleのサムネイルをバルク更新する.idとthumbnail必須.
     *
     * @param articleList 更新するThumbnailがsetされているArticleリスト
     */
//    fun bulkUpdateThumbnailArticles(articleList: List<Article>?) {
//        if (articleList == null || articleList.isEmpty()) {
//            return
//        }
//        // 100件ずつUpdateしていく
//        val queue = ArrayDeque(articleList)
//        try {
//            while (!queue.isEmpty()) {
//                val sql = StringBuilder()
//                sql.append("UPDATE articles SET ")
//                sql.append("thumbnail = ")
//                sql.append("CASE id ")
//                val parameterSize = queue.size.coerceAtMost(MAX_BULK_SIZE)
//                val parameterWhenList = Collections.nCopies(parameterSize, " WHEN ? THEN ? ")
//                sql.append(parameterWhenList.joinToString(separator = " "))
//                sql.append(" END ")
//                val parameterInList = Collections.nCopies(parameterSize, "?")
//                sql.append(" WHERE id IN ( ")
//                sql.append(parameterInList.joinToString(separator = ","))
//                sql.append(" ) ")
//                val query = entityManager!!.createNativeQuery(sql.toString())
//                var parameterPosition = 0
//                for (j in 0 until parameterSize) {
//                    val parameterArticle: Article = queue.remove()
//                    // WHEN ... THEN... 句
//                    query.setParameter(++parameterPosition, parameterArticle.id)
//                    query.setParameter(++parameterPosition, parameterArticle.thumbnail)
//                    // IN句
//                    var space = parameterPosition + (parameterSize - j - 1) * 2 + j
//                    query.setParameter(++space, parameterArticle.id)
//                }
//                query.executeUpdate()
//            }
//        } catch (e: Exception) {
//            logger.error("An error has occurred on update article thumbnail", e)
//        }
//    }
}
