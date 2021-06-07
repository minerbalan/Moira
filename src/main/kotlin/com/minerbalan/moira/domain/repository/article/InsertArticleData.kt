package com.minerbalan.moira.domain.repository.article

import java.time.LocalDateTime

data class InsertArticleData(
    var subscriptionId: Long,

    var url: String,

    var title: String,

    var description: String?,

    var thumbnail: String? ,

    var createdAt: LocalDateTime,

    var publishedAt: LocalDateTime
)
