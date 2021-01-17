package com.minerbalan.moira.domain.repository

import com.minerbalan.moira.domain.entity.Subscription
import org.springframework.stereotype.Repository

@Repository
interface SubscriptionsRepository {
    fun fetchSubscriptionList(): List<Subscription?>
}
