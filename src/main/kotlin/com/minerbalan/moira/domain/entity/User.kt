package com.minerbalan.moira.domain.entity

import java.time.LocalDateTime

data class User(
    var id: Long?,
    var userName: String,
    var email: String,
    var password: String,
    var createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)
