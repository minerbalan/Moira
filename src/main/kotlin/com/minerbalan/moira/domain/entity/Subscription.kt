package com.minerbalan.moira.domain.entity

import java.time.LocalDateTime

data class Subscription(
        var id: Long?,

        var name: String,

        var url: String,

        var createdAt: LocalDateTime,

        var lastFetchedAt: LocalDateTime? = null,

        var updatedAt: LocalDateTime? = null,

        var deletedAt: LocalDateTime? = null
)
