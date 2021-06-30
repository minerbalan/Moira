package com.minerbalan.moira.database

import com.minerbalan.moira.database.table.UsersTable
import com.minerbalan.moira.database.table.toUserEntity
import com.minerbalan.moira.domain.entity.UserEntity
import com.minerbalan.moira.domain.repository.user.InsertUserData
import com.minerbalan.moira.domain.repository.user.UsersRepository
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Repository

@Repository
class UsersRepositoryImpl : UsersRepository {
    override fun createUser(userData: InsertUserData) {
        UsersTable.insert {
            it[userName] = userData.userName
            it[email] = userData.email
            it[password] = userData.password
            it[createdAt] = userData.createdAt
        }
    }

    override fun existsUser(email: String): Boolean {
        val count = UsersTable.select { UsersTable.email eq email }.count()
        return count != 0L
    }

    override fun findUserFromEmail(email: String): UserEntity? {
        val query = UsersTable.select { UsersTable.email eq email }
        for (item in query) {
            return item.toUserEntity()
        }
        return null
    }
}
