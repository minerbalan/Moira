package com.minerbalan.moira.database.table

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object UsersSubscriptionsTable : Table("users_subscriptions") {
    val usersId =
        reference("users_id", UsersTable.id, onUpdate = ReferenceOption.CASCADE, onDelete = ReferenceOption.CASCADE)

    val subscriptionsId = reference(
        "subscriptions_id",
        SubscriptionsTable.id,
        onUpdate = ReferenceOption.CASCADE,
        onDelete = ReferenceOption.CASCADE
    )

    val name = varchar("name", 255)
    val createdAt = datetime("created_at")

    override val primaryKey = PrimaryKey(usersId, subscriptionsId)
}
