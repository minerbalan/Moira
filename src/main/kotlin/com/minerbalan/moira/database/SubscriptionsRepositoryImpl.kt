package com.minerbalan.moira.database

import com.minerbalan.moira.database.rowmapper.SubscriptionRowMapper
import com.minerbalan.moira.database.table.SubscriptionsTable
import com.minerbalan.moira.database.table.UsersSubscriptionsTable
import com.minerbalan.moira.database.table.toSubscriptionEntity
import com.minerbalan.moira.domain.entity.SubscriptionEntity
import com.minerbalan.moira.domain.repository.subscription.InsertSubscriptionData
import com.minerbalan.moira.domain.repository.subscription.SubscriptionsRepository
import org.jetbrains.exposed.sql.*
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class SubscriptionsRepositoryImpl(private val jdbcTemplate: JdbcTemplate) : SubscriptionsRepository {
    /**
     * 新しくsubscriptionを追加する
     */
    override fun insertSubscription(subscriptionData: InsertSubscriptionData) {
        SubscriptionsTable.insert {
            it[url] = subscriptionData.url
            it[createdAt] = subscriptionData.createdAt
            it[lastFetchedAt] = subscriptionData.lastFetchedAt
        }
    }

    /**
     * 購読リストを取得する.
     */
    override fun fetchSubscriptionList(): List<SubscriptionEntity> {
        val query = SubscriptionsTable
            .select { SubscriptionsTable.deletedAt eq null }
            .orderBy(SubscriptionsTable.createdAt, SortOrder.DESC)
        val result = ArrayList<SubscriptionEntity>()
        for (item in query) {
            result.add(item.toSubscriptionEntity())
        }
        return result;
    }

    override fun existsSubscription(url: String): Boolean {
        val subscription = getSubscriptionByUrl(url)
        return subscription != null
    }

    override fun getSubscriptionByUrl(url: String): SubscriptionEntity? {
        val query = SubscriptionsTable
            .select { (SubscriptionsTable.url eq url) and (SubscriptionsTable.deletedAt eq null) }
            .orderBy(SubscriptionsTable.createdAt, SortOrder.DESC)
            .limit(1)

        for (item in query){
            return item.toSubscriptionEntity()
        }
        return null
    }

    override fun isUserSubscribe(userId: Long, subscriptionId: Long): Boolean {
        val count = UsersSubscriptionsTable
            .select { (UsersSubscriptionsTable.usersId eq userId) and (UsersSubscriptionsTable.subscriptionsId eq subscriptionId) }
            .count()
        return count != 0L
    }

    override fun linkingUserAndSubscription(userId: Long, subscriptionId: Long, subscriptionName: String, createdAt: LocalDateTime) {
        UsersSubscriptionsTable.insert {
            it[usersId] = userId
            it[subscriptionsId] = subscriptionId
            it[name] = subscriptionName
            it[this.createdAt] = createdAt
        }
    }
}
