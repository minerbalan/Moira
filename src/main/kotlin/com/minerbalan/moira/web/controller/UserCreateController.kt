package com.minerbalan.moira.web.controller

import com.minerbalan.moira.usecase.UserUseCase
import com.minerbalan.moira.web.request.UserCreatePostRequest
import com.minerbalan.moira.web.response.BasicResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserCreateController(
    val userUseCase: UserUseCase
) {
    @PostMapping("/users")
    fun createUserHandler(
        @RequestBody @Validated request: UserCreatePostRequest,
        bindingResult: BindingResult
    ): ResponseEntity<BasicResponse> {
        val username = request.username
        val email = request.email
        val rawPassword = request.password
        if (bindingResult.hasErrors() || username == null || email == null || rawPassword == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BasicResponse(bindingResult.objectName))
        }

        val createResult = userUseCase.createUser(username, email, rawPassword)

        if (!createResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BasicResponse(createResult.getErrorMessage()))
        }

        return ResponseEntity.status(HttpStatus.OK).body(BasicResponse("Register Success"))
    }
}
