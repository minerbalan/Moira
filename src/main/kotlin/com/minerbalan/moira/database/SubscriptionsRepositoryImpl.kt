package com.minerbalan.moira.database

import com.minerbalan.moira.database.rowmapper.SubscriptionRowMapper
import com.minerbalan.moira.domain.entity.Subscription
import com.minerbalan.moira.domain.repository.SubscriptionsRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class SubscriptionsRepositoryImpl(private val jdbcTemplate: JdbcTemplate) : SubscriptionsRepository {
    /**
     * 新しくsubscriptionを追加する
     */
    override fun insertSubscription(subscription: Subscription) {
        jdbcTemplate.update(
            "INSERT INTO subscriptions(url, created_at, last_fetched_at) VALUES (?,?,?)",
            subscription.url, subscription.createdAt, subscription.lastFetchedAt
        )
    }

    /**
     * 購読リストを取得する.
     */
    override fun fetchSubscriptionList(): List<Subscription> {
        return jdbcTemplate.query(
            "SELECT * FROM subscriptions WHERE deleted_at IS NULL ORDER BY created_at DESC",
            SubscriptionRowMapper()
        )
    }

    override fun existsSubscription(url: String): Boolean {
        val subscription = getSubscriptionByUrl(url)
        return subscription != null
    }

    override fun getSubscriptionByUrl(url: String): Subscription? {
        val list = jdbcTemplate.query(
            "SELECT * FROM subscriptions WHERE url = ? AND deleted_at IS NULL ORDER BY created_at DESC",
            SubscriptionRowMapper(), url
        )
        if (list.isEmpty()) {
            return null
        }
        return list[0]
    }

    override fun isUserSubscribe(userId: Long, subscriptionId: Long): Boolean {
        val result = jdbcTemplate.queryForList(
            "SELECT users_id FROM users_subscriptions WHERE users_id = ? AND subscriptions_id = ? ",
            userId,
            subscriptionId
        )
        return result.isNotEmpty()
    }

    override fun linkingUserAndSubscription(userId: Long, subscriptionId: Long, subscriptionName: String) {
        jdbcTemplate.update(
            "INSERT INTO users_subscriptions(users_id, subscriptions_id, name, created_at) VALUES (?,?,?,?)",
            userId, subscriptionId, subscriptionName, LocalDateTime.now()
        )
    }
}
