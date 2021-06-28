package com.minerbalan.moira.domain.repository.subscription

import com.minerbalan.moira.domain.entity.SubscriptionEntity
import java.time.LocalDateTime

interface SubscriptionsRepository {
    /**
     * 新しくsubscriptionを追加する
     */
    fun insertSubscription(subscriptionData: InsertSubscriptionData)

    /**
     * 購読リストを取得する.
     */
    fun fetchSubscriptionList(): List<SubscriptionEntity>

    /**
     * 購読urlが既に存在しているか確認する
     */
    fun existsSubscription(url: String): Boolean

    /**
     * URLから購読Objectを取得する
     */
    fun getSubscriptionByUrl(url: String): SubscriptionEntity?

    /**
     * ユーザーが購読しているか
     */
    fun isUserSubscribe(userId: Long, subscriptionId: Long): Boolean

    /**
     * ユーザーと購読を紐づける
     */
    fun linkingUserAndSubscription(userId: Long, subscriptionId: Long, subscriptionName: String, createdAt: LocalDateTime)
}
