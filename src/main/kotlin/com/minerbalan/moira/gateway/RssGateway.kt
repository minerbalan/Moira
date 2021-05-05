package com.minerbalan.moira.gateway

import com.minerbalan.moira.domain.entity.Article
import com.minerbalan.moira.domain.entity.Subscription

interface RssGateway {
    fun fetchArticleFromSubscriptions(subscriptionList: List<Subscription>): List<Article>

    fun fetchFeedTitle(url: String): String
}
