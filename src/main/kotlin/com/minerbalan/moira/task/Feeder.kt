package com.minerbalan.moira.task

import com.minerbalan.moira.usecase.rss.RssUseCase
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class Feeder(private val rssUseCase: RssUseCase) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Scheduled(initialDelay = 1000, fixedRate = 600000)
    @Transactional
    fun doFeeder() {
        logger.info("start doFeeder")
        rssUseCase.fetchArticleFromSubscription()
    }
}
