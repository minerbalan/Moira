package com.minerbalan.moira.service

import com.minerbalan.moira.domain.entity.Article
import com.rometools.rome.feed.synd.SyndFeed
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class FeedService {
    companion object{
        /**
         * SyndFeedをArticleに変換する.URIとTitle,Description,PublishDateを変換する
         * PublishDateが存在しない場合は現在時刻がPublishDateになる
         *
         * @param syndFeed       取得したFeed
         * @param subscriptionId 取得したFeedのSubscriptionId
         * @return 変換したArticle.
         */
        fun getArticleFromFeed(syndFeed: SyndFeed, subscriptionId: Long): List<Article> {
            val articleList = ArrayList<Article>()
            for (feed in syndFeed.entries) {
                val article = Article(subscriptionId = subscriptionId)
                article.url = feed.uri
                article.title = feed.title
                article.description = feed.description.value
                val publishDate = feed.publishedDate
                //publishDateが存在しない場合、現在時刻をpublishDateにする
                if (publishDate == null) {
                    article.publishedAt = LocalDateTime.now()
                } else {
                    article.publishedAt = LocalDateTime.ofInstant(publishDate.toInstant(), ZoneId.of("Asia/Tokyo"))
                }
                articleList.add(article)
            }
            return articleList
        }
    }
}
