package com.minerbalan.moira.javaconfig

import com.minerbalan.moira.usecase.UserAuthUseCase
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class UserDetailsServiceImpl(private val userAuthUseCase: UserAuthUseCase) : UserDetailsService {

    override fun loadUserByUsername(email: String?): UserDetails {
        if (email.isNullOrEmpty()) {
            throw UsernameNotFoundException("username is null or empty")
        }
        val user = userAuthUseCase.findUserFromEmail(email) ?: throw UsernameNotFoundException("")

        return UserDetailsImpl(user)
    }
}
