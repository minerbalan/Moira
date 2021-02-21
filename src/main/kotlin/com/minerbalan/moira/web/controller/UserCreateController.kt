package com.minerbalan.moira.web.controller

import com.minerbalan.moira.domain.entity.User
import com.minerbalan.moira.domain.repository.UsersRepository
import com.minerbalan.moira.web.request.UserCreatePostRequest
import com.minerbalan.moira.web.response.BasicResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception
import java.time.LocalDateTime

@RestController
class UserCreateController(val passwordEncoder: PasswordEncoder, val usersRepository: UsersRepository) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @PostMapping("/users")
    fun createUserHandler(@RequestBody @Validated request: UserCreatePostRequest, bindingResult: BindingResult): ResponseEntity<BasicResponse> {
        val username = request.username
        val email = request.email
        val rawPassword = request.password
        if (bindingResult.hasErrors() || username == null || email == null || rawPassword == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BasicResponse(bindingResult.objectName))
        }

        try {
            if (usersRepository.existsUser(email)) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BasicResponse("Exists User"))
            }
            val user = User(id = null, userName = username, email = email,
                    password = passwordEncoder.encode(rawPassword), createdAt = LocalDateTime.now(), updatedAt = null)
            usersRepository.createUser(user)
        } catch (e: Exception) {
            logger.error("Error happen", e)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BasicResponse("Error happen"))
        }

        return ResponseEntity.status(HttpStatus.OK).body(BasicResponse("Register Success"))
    }
}
