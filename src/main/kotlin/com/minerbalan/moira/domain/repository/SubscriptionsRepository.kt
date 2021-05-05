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
    fun fetchSubscriptionList(): List<Subscription>

    /**
     * 購読urlが既に存在しているか確認する
     */
    fun existsSubscription(url: String): Boolean

    /**
     * URLから購読Objectを取得する
     */
    fun getSubscriptionByUrl(url: String): Subscription?

    /**
     * ユーザーが購読しているか
     */
    fun isUserSubscribe(userId: Long, subscriptionId: Long): Boolean

    /**
     * ユーザーと購読を紐づける
     */
    fun linkingUserAndSubscription(userId: Long, subscriptionId: Long, subscriptionName: String)
}
