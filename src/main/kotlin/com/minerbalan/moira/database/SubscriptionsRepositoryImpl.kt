package com.minerbalan.moira.database

import com.minerbalan.moira.database.rowmapper.SubscriptionRowMapper
import com.minerbalan.moira.domain.entity.Subscription
import com.minerbalan.moira.domain.repository.SubscriptionsRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class SubscriptionsRepositoryImpl(private val jdbcTemplate: JdbcTemplate) : SubscriptionsRepository {
    /**
     * 新しくsubscriptionを追加する
     */
    override fun insertSubscription(subscription: Subscription) {
        jdbcTemplate.update(
                "INSERT INTO subscriptions(name, url, created_at, last_fetched_at) VALUES (?,?,?,?)",
                subscription.name, subscription.url, subscription.createdAt, subscription.lastFetchedAt)
    }

    /**
     * 購読リストを取得する.
     */
    override fun fetchSubscriptionList(): List<Subscription?> {
        return jdbcTemplate.query(
                "SELECT * FROM subscriptions ORDER BY created_at DESC",
                SubscriptionRowMapper()
        )
    }
}
