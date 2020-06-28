package com.minerbalan.moira.task

import com.minerbalan.moira.repository.ArticlesRepository
import com.minerbalan.moira.service.ArticleService
import com.minerbalan.moira.service.OgpService
import com.minerbalan.moira.service.RssFetchService
import com.minerbalan.moira.service.SubscriptionsService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class Feeder(private val subscriptionsService: SubscriptionsService, private val articleService: ArticleService,
             private val articlesRepository: ArticlesRepository, private val ogpService: OgpService) {
    private val logger = LoggerFactory.getLogger(this.javaClass)


    /**
     * 　RSSフィードを取得し、DBに登録する.
     */
    @Scheduled(initialDelay = 1000, fixedRate = 600000)
    @Transactional
    fun doFeeder() {
        logger.info("start doFeeder")
        val subscriptionList = subscriptionsService.subscriptionList
        val rssFetchService = RssFetchService()
        val articleList = rssFetchService.getFeedsList(subscriptionList)
        articleService.bulkInsertOrIgnoreArticles(articleList)
        val thumbnailNullArticle = articlesRepository.findByThumbnailIsNull()
        ogpService.getOgpProperties(thumbnailNullArticle)
        //articleService.bulkUpdateThumbnailArticles(thumbnailSetArticle)
    }

}