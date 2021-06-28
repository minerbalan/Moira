package com.minerbalan.moira.domain.repository.subscription

import java.time.LocalDateTime

data class InsertSubscriptionData (
    var url: String,

    var createdAt: LocalDateTime,

    var lastFetchedAt: LocalDateTime? = null,
)
