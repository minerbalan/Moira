package com.minerbalan.moira.service

import com.minerbalan.moira.domain.entity.Article
import com.minerbalan.moira.domain.entity.Subscription
import com.rometools.rome.io.FeedException
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader

import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.impl.client.HttpClients
import org.slf4j.LoggerFactory
import java.io.IOException
import java.io.InputStream
import java.time.LocalDateTime
import java.util.*

class RssFetchService {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    /**
     * Listに入っているSubscriptionからURLからRSSFeedを取得し、Articleのリストをreturnする.
     *
     * @param subscriptionList 取得するSubscription
     * @return 取得したArticle. Subscriptionごとに分けることなく、一括でreturnする
     */
    fun getFeedsList(subscriptionList: List<Subscription?>): List<Article> {
        val articleList = ArrayList<Article>()
        for (subscription in subscriptionList) {
            if (subscription == null) continue
            val url = subscription.url
            val httpUriRequest: HttpUriRequest = HttpGet(url)
            try {
                HttpClients.createMinimal().use { closeableHttpClient ->
                    closeableHttpClient.execute(httpUriRequest).use { closeableHttpResponse ->
                        val inputStream: InputStream = closeableHttpResponse.entity.content
                        val syndFeedInput = SyndFeedInput()
                        val syndFeed = syndFeedInput.build(XmlReader(inputStream))
                        val feedArticleList = FeedService.getArticleFromFeed(syndFeed, subscription.id)
                        articleList.addAll(feedArticleList)
                        subscription.lastFetchedAt = LocalDateTime.now()
                    }
                }
            } catch (e: IOException) {
                logger.error("An error has occurred on getting feed", e)
            } catch (e: FeedException) {
                logger.error("An error has occurred on getting feed", e)
            }
        }
        return articleList
    }
}
