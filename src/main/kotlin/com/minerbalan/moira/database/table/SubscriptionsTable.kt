package com.minerbalan.moira.database.table

import com.minerbalan.moira.domain.entity.SubscriptionEntity
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object SubscriptionsTable : Table("subscriptions") {
    val id = long("id").autoIncrement()
    val url = varchar("url", 255)
    val createdAt = datetime("created_at")
    val lastFetchedAt = datetime("last_fetched_at").nullable()
    val updatedAt = datetime("updated_at").nullable()
    val deletedAt = datetime("deleted_at").nullable()

    override val primaryKey = PrimaryKey(id)
}

fun ResultRow.toSubscriptionEntity(): SubscriptionEntity {
    return SubscriptionEntity(
        id = this[SubscriptionsTable.id],
        url = this[SubscriptionsTable.url],
        createdAt = this[SubscriptionsTable.createdAt],
        lastFetchedAt = this[SubscriptionsTable.lastFetchedAt],
        updatedAt = this[SubscriptionsTable.updatedAt],
        deletedAt = this[SubscriptionsTable.deletedAt]
    )
}
