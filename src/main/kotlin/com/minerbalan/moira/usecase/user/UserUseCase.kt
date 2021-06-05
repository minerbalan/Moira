package com.minerbalan.moira.usecase.user

import com.minerbalan.moira.domain.entity.UserEntity
import com.minerbalan.moira.usecase.UseCaseResult

interface UserUseCase {
    fun createUser(username: String, email: String, rawPassword: String) : UseCaseResult

    fun existsUser(email: String): Boolean

    fun findUserFromEmail(email: String): UserEntity?
}
