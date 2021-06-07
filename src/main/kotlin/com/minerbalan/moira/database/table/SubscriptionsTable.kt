package com.minerbalan.moira.database.table

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object SubscriptionsTable : Table("subscriptions") {
    val id = long("id").autoIncrement()
    val name = varchar("name", 255).uniqueIndex()
    val url = varchar("url", 255)
    val createdAt = datetime("created_at")
    val lastFetchedAt = datetime("last_fetched_at")
    val updatedAt = datetime("updated_at").nullable()
    val deletedAt = datetime("deleted_at").nullable()

    override val primaryKey = PrimaryKey(id)
}
