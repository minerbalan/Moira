package com.minerbalan.moira.domain.repository.user

import java.time.LocalDateTime

data class InsertUserData(
    var userName: String,
    var email: String,
    var password: String,
    var createdAt: LocalDateTime
)
