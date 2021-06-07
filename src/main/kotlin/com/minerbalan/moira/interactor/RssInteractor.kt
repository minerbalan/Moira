package com.minerbalan.moira.interactor

import com.minerbalan.moira.domain.repository.article.ArticlesRepository
import com.minerbalan.moira.domain.repository.SubscriptionsRepository
import com.minerbalan.moira.gateway.rss.RssGateway
import com.minerbalan.moira.gateway.ScrapingGateway
import com.minerbalan.moira.gateway.rss.toInsertArticleData
import com.minerbalan.moira.usecase.rss.RssUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RssInteractor(
    private val subscriptionsRepository: SubscriptionsRepository,
    private val articlesRepository: ArticlesRepository,
    private val rssGateway: RssGateway,
    private val scrapingGateway: ScrapingGateway
) : RssUseCase {
    /**
     * 　RSSフィードを取得し、DBに登録する.
     */
    override fun fetchArticleFromSubscription() {
        val subscriptionList = subscriptionsRepository.fetchSubscriptionList()
        val rssDataList = rssGateway.fetchArticleFromSubscriptions(subscriptionList)
        val insertArticleData = rssDataList.map { it.toInsertArticleData() }
        articlesRepository.bulkInsertOrIgnoreArticles(insertArticleData)
        val thumbnailNullArticle = articlesRepository.findByThumbnailIsNull()
        for (article in thumbnailNullArticle) {
            var thumbnailUrl = scrapingGateway.fetchOgpImageProperties(article.url)
            if (thumbnailUrl == null) {
                thumbnailUrl = ""
            }
            article.thumbnail = thumbnailUrl
        }
        articlesRepository.bulkUpdateThumbnailArticles(thumbnailNullArticle)
    }
}
