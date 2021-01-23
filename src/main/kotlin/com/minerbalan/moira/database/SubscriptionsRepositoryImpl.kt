package com.minerbalan.moira.database

import com.minerbalan.moira.domain.entity.Subscription
import com.minerbalan.moira.domain.repository.SubscriptionsRepository
import org.springframework.stereotype.Repository

@Repository
class SubscriptionsRepositoryImpl : SubscriptionsRepository {
    override fun fetchSubscriptionList(): List<Subscription?> {
        TODO("Not yet implemented")
    }
}
