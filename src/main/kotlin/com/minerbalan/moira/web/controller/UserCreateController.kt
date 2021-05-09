package com.minerbalan.moira.web.controller

import com.minerbalan.moira.usecase.UserUseCase
import com.minerbalan.moira.web.request.UserCreatePostRequest
import com.minerbalan.moira.web.response.BasicResponse
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class UserCreateController(
    val userUseCase: UserUseCase
) {
    @PostMapping("/users")
    fun createUserHandler(
        @RequestBody @Validated request: UserCreatePostRequest
    ): BasicResponse {
        val username = request.username
        val email = request.email
        val rawPassword = request.password
        if (username == null || email == null || rawPassword == null) {
            throw IllegalArgumentException()
        }

        val createResult = userUseCase.createUser(username, email, rawPassword)

        if (!createResult.isSuccess()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, createResult.getErrorMessage())
        }

        return BasicResponse(true, "Register Success")
    }
}
