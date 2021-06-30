package com.minerbalan.moira.domain.repository.user

import com.minerbalan.moira.domain.entity.UserEntity

interface UsersRepository {
    fun createUser(userData: InsertUserData)

    fun existsUser(email: String): Boolean

    fun findUserFromEmail(email: String): UserEntity?
}
