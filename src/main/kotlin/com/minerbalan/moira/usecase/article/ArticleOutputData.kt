package com.minerbalan.moira.usecase.article

import java.time.LocalDateTime

data class ArticleOutputData(
    val id: Long,
    val url: String,
    val title: String,
    val description: String?,
    val thumbnail: String?,
    val subscriptionName: String,
    val publishedAt: LocalDateTime
)
