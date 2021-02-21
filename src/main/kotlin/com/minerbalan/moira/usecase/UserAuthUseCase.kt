package com.minerbalan.moira.usecase

import com.minerbalan.moira.domain.entity.User
import com.minerbalan.moira.domain.repository.UsersRepository
import org.springframework.stereotype.Service

@Service
class UserAuthUseCase(private val usersRepository: UsersRepository) {
    fun createUser(user: User) {
        usersRepository.createUser(user)
    }

    fun existsUser(email: String){
        usersRepository.existsUser(email)
    }

    fun findUserFromEmail(email: String): User? {
        return usersRepository.findUserFromEmail(email)
    }
}
