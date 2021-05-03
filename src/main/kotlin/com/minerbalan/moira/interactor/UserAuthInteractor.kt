package com.minerbalan.moira.interactor

import com.minerbalan.moira.domain.entity.User
import com.minerbalan.moira.domain.repository.UsersRepository
import com.minerbalan.moira.usecase.UserAuthUseCase
import org.springframework.stereotype.Service

@Service
class UserAuthInteractor(private val usersRepository: UsersRepository) : UserAuthUseCase {
    override fun createUser(user: User) {
        usersRepository.createUser(user)
    }

    override fun existsUser(email: String): Boolean {
        return usersRepository.existsUser(email)
    }

    override fun findUserFromEmail(email: String): User? {
        return usersRepository.findUserFromEmail(email)
    }
}
