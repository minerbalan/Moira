package com.minerbalan.moira.domain.repository

import com.minerbalan.moira.domain.entity.Subscription

interface SubscriptionsRepository {
    /**
     * 新しくsubscriptionを追加する
     */
    fun insertSubscription(subscription: Subscription)

    /**
     * 購読リストを取得する.
     */
    fun fetchSubscriptionList(): List<Subscription?>
}
