package com.minerbalan.moira.domain.repository

import com.minerbalan.moira.domain.entity.Subscription

interface SubscriptionsRepository {
    fun fetchSubscriptionList(): List<Subscription?>
}
