package com.minerbalan.moira.domain.repository

import com.minerbalan.moira.domain.entity.User

interface UsersRepository {
    fun createUser(user: User)

    fun existsUser(email: String): Boolean

    fun findUserFromEmail(email: String): User?
}
