package com.minerbalan.moira.interactor

import com.minerbalan.moira.domain.entity.UserEntity
import com.minerbalan.moira.domain.repository.UsersRepository
import com.minerbalan.moira.usecase.UseCaseResult
import com.minerbalan.moira.usecase.user.UserUseCase
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserInteractor(
    private val usersRepository: UsersRepository,
    private val passwordEncoder: PasswordEncoder
) : UserUseCase {
    override fun createUser(username: String, email: String, rawPassword: String): UseCaseResult {
        if (usersRepository.existsUser(email)) {
            return InteractorResult.createErrorResult("すでにユーザーが存在しています")
        }
        val user = UserEntity(
            id = null, userName = username, email = email,
            password = passwordEncoder.encode(rawPassword), createdAt = LocalDateTime.now(), updatedAt = null
        )
        usersRepository.createUser(user)
        return InteractorResult.createSuccessResult()
    }

    override fun existsUser(email: String): Boolean {
        return usersRepository.existsUser(email)
    }

    override fun findUserFromEmail(email: String): UserEntity? {
        return usersRepository.findUserFromEmail(email)
    }
}
