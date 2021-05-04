package com.minerbalan.moira.interactor

import com.minerbalan.moira.domain.entity.User
import com.minerbalan.moira.domain.repository.UsersRepository
import com.minerbalan.moira.usecase.UseCaseResult
import com.minerbalan.moira.usecase.UserUseCase
import com.minerbalan.moira.web.response.BasicResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
        val user = User(
            id = null, userName = username, email = email,
            password = passwordEncoder.encode(rawPassword), createdAt = LocalDateTime.now(), updatedAt = null
        )
        usersRepository.createUser(user)
        return InteractorResult.createSuccessResult()
    }

    override fun existsUser(email: String): Boolean {
        return usersRepository.existsUser(email)
    }

    override fun findUserFromEmail(email: String): User? {
        return usersRepository.findUserFromEmail(email)
    }
}
