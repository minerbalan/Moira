package com.minerbalan.moira.domain.repository

import com.minerbalan.moira.domain.entity.UserEntity

interface UsersRepository {
    fun createUser(userEntity: UserEntity)

    fun existsUser(email: String): Boolean

    fun findUserFromEmail(email: String): UserEntity?
}
