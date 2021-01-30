package com.minerbalan.moira.domain.entity

import java.time.LocalDateTime

data class Article(
        var id: Long? = null,

        var subscriptionId: Long,

        var url: String? = null,

        var title: String? = null,

        var description: String? = null,

        var thumbnail: String? = null,

        var createdAt: LocalDateTime? = null,

        var publishedAt: LocalDateTime? = null
)
