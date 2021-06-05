package com.minerbalan.moira.gateway

import com.minerbalan.moira.domain.entity.ArticleEntity
import com.minerbalan.moira.domain.entity.SubscriptionEntity

interface RssGateway {
    fun fetchArticleFromSubscriptions(subscriptionEntityList: List<SubscriptionEntity>): List<ArticleEntity>

    fun fetchFeedTitle(url: String): String
}
