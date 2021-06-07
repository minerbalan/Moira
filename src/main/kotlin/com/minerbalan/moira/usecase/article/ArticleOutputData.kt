package com.minerbalan.moira.usecase.article

data class ArticleOutputData(
    val id: Long,
    val url: String,
    val title: String,
    val description: String?,
    val thumbnail: String?
)
