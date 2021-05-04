package com.minerbalan.moira.usecase

import com.minerbalan.moira.domain.entity.User

interface UserUseCase {
    fun createUser(username: String, email: String, rawPassword: String) : UseCaseResult

    fun existsUser(email: String): Boolean

    fun findUserFromEmail(email: String): User?
}
