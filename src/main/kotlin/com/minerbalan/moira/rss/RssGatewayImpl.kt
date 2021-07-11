package com.minerbalan.moira.rss

import com.minerbalan.moira.domain.entity.SubscriptionEntity
import com.minerbalan.moira.gateway.rss.RssArticleOutputData
import com.minerbalan.moira.gateway.rss.RssGateway
import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.io.FeedException
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.impl.client.HttpClients
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.IOException
import java.io.InputStream
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.ArrayList

@Service
class RssGatewayImpl : RssGateway {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun fetchArticleFromSubscriptions(subscriptionEntityList: List<SubscriptionEntity>): List<RssArticleOutputData> {
        val articleList = ArrayList<RssArticleOutputData>()
        for (subscription in subscriptionEntityList) {
            val subscriptionId = subscription.id
            val url = subscription.url
            val httpUriRequest: HttpUriRequest = HttpGet(url)
            try {
                HttpClients.createMinimal().use { closeableHttpClient ->
                    closeableHttpClient.execute(httpUriRequest).use { closeableHttpResponse ->
                        val inputStream: InputStream = closeableHttpResponse.entity.content
                        val syndFeedInput = SyndFeedInput()
                        val syndFeed = syndFeedInput.build(XmlReader(inputStream))
                        val feedArticleList = getArticleFromFeed(syndFeed, subscriptionId)
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

    /**
     * @exception FeedException
     * @exception IOException
     */
    override fun fetchFeedTitle(url: String): String {
        val httpUriRequest: HttpUriRequest = HttpGet(url)
        HttpClients.createMinimal().use { closeableHttpClient ->
            closeableHttpClient.execute(httpUriRequest).use { closeableHttpResponse ->
                val inputStream: InputStream = closeableHttpResponse.entity.content
                val syndFeedInput = SyndFeedInput()
                val syndFeed = syndFeedInput.build(XmlReader(inputStream))
                return syndFeed.title
            }
        }
    }

    /**
     * SyndFeedをArticleに変換する.URIとTitle,Description,PublishDateを変換する
     * PublishDateが存在しない場合は現在時刻がPublishDateになる
     *
     * @param syndFeed       取得したFeed
     * @param subscriptionId 取得したFeedのSubscriptionId
     * @return 変換したArticle.
     */
    private fun getArticleFromFeed(syndFeed: SyndFeed, subscriptionId: Long): List<RssArticleOutputData> {
        val articleList = ArrayList<RssArticleOutputData>()
        for (feed in syndFeed.entries) {
            //publishDateが存在しない場合、現在時刻をpublishDateにする
            val publishDate: LocalDateTime = if (feed.publishedDate == null) {
                LocalDateTime.now()
            } else {
                LocalDateTime.ofInstant(feed.publishedDate.toInstant(), ZoneId.of("Asia/Tokyo"))
            }
            val article = RssArticleOutputData(
                subscriptionId = subscriptionId,
                url = feed.uri,
                title = feed.title,
                description = feed.description.value,
                createdAt = LocalDateTime.now(),
                publishedAt = publishDate
            )

            articleList.add(article)
        }
        return articleList
    }
}
