package com.minerbalan.moira.gateway.rss

import com.minerbalan.moira.domain.entity.ArticleEntity
import com.minerbalan.moira.domain.entity.SubscriptionEntity

interface RssGateway {
    fun fetchArticleFromSubscriptions(subscriptionEntityList: List<SubscriptionEntity>): List<RssArticleOutputData>

    fun fetchFeedTitle(url: String): String
}
