package com.minerbalan.moira.domain.entity

import java.time.LocalDateTime

data class SubscriptionEntity(
    var id: Long,

    var url: String,

    var createdAt: LocalDateTime,

    var lastFetchedAt: LocalDateTime?,

    var updatedAt: LocalDateTime? = null,

    var deletedAt: LocalDateTime? = null
)
