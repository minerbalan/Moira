package com.minerbalan.moira.domain.entity

import java.time.LocalDateTime

data class UserSubscriptionEntity(
    var usersId: Long,
    var subscriptionId: Long,
    var name: String,
    var createdAt: LocalDateTime?
)
