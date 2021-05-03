package com.minerbalan.moira.interactor

import com.minerbalan.moira.domain.entity.Subscription
import com.minerbalan.moira.domain.repository.SubscriptionsRepository
import com.minerbalan.moira.service.RssFetchService
import com.minerbalan.moira.usecase.SubscriptionUseCase
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SubscriptionInteractor(val subscriptionsRepository: SubscriptionsRepository) : SubscriptionUseCase {
    override fun createSubscription(url: String, name: String?) {
        val rssFetchService = RssFetchService()
        val feedName = rssFetchService.getFeedTitle(url) ?: throw IllegalArgumentException()
        val subscriptionName = name ?: feedName

        val subscription = Subscription(
            id = null,
            name = subscriptionName,
            url = url,
            createdAt = LocalDateTime.now(),
            lastFetchedAt = LocalDateTime.now()
        )
        subscriptionsRepository.insertSubscription(subscription)
    }

    override fun existsSubscription(url: String): Boolean {
        return subscriptionsRepository.existsSubscription(url)
    }
}
