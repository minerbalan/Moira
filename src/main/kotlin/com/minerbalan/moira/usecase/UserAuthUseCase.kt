package com.minerbalan.moira.usecase

import com.minerbalan.moira.domain.entity.User

interface UserAuthUseCase {
    fun createUser(user: User)

    fun existsUser(email: String): Boolean

    fun findUserFromEmail(email: String): User?
}
