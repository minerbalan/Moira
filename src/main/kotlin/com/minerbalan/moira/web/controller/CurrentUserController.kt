package com.minerbalan.moira.web.controller

import com.minerbalan.moira.usecase.UserAuthUseCase
import com.minerbalan.moira.web.response.UserResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CurrentUserController(private val userAuthUseCase: UserAuthUseCase) {

    @GetMapping("/current/user")
    fun getCurrentUser(@AuthenticationPrincipal user: UserDetails?): UserResponse {
        if (user == null) {
            throw IllegalArgumentException("userがnull") //認証していない場合、通常はSpringSecurityに弾かれる
        }
        val loginUser = userAuthUseCase.findUserFromEmail(user.username)
                ?: throw IllegalArgumentException("emailと一致するユーザーがいない")
        return UserResponse(username = loginUser.userName)
    }
}
