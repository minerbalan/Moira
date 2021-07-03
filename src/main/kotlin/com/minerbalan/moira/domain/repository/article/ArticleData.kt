package com.minerbalan.moira.domain.repository.article

import java.time.LocalDateTime

data class ArticleData(
    val id: Long,
    val subscriptionId: Long,
    val url: String,
    val title: String,
    val description: String?,
    val thumbnail: String? = null,
    val publishedAt: LocalDateTime,
    val subscriptionName: String
)
