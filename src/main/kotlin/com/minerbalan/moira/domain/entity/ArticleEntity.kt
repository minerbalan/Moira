package com.minerbalan.moira.domain.entity

import java.time.LocalDateTime

data class ArticleEntity(
    var id: Long? = null,

    var subscriptionId: Long,

    var url: String,

    var title: String,

    var description: String?,

    var thumbnail: String? = null,

    var createdAt: LocalDateTime,

    var publishedAt: LocalDateTime? = null
)
