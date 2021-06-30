package com.minerbalan.moira.database.table

import com.minerbalan.moira.domain.entity.UserEntity
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object UsersTable : Table("users") {
    val id = long("id").autoIncrement()
    val userName = varchar("user_name", 255)
    val email = varchar("email", 511).uniqueIndex()
    val password = varchar("password", 255)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at").nullable()

    override val primaryKey = PrimaryKey(id)
}

fun ResultRow.toUserEntity(): UserEntity {
    return UserEntity(
        id = this[UsersTable.id],
        userName = this[UsersTable.userName],
        email = this[UsersTable.email],
        password = this[UsersTable.password],
        createdAt = this[UsersTable.createdAt],
        updatedAt = this[UsersTable.updatedAt],
    )
}
