package com.minerbalan.moira.usecase

import com.minerbalan.moira.domain.entity.Subscription
import com.minerbalan.moira.domain.repository.SubscriptionsRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SubscriptionCreateUseCase(val subscriptionsRepository: SubscriptionsRepository) {
    fun createSubscription(name: String, url: String) {
        val subscription = Subscription(id = null,
                name = name,
                url = url,
                createdAt = LocalDateTime.now(),
                lastFetchedAt = LocalDateTime.now())
        subscriptionsRepository.insertSubscription(subscription)
    }
}
