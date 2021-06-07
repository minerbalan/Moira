package com.minerbalan.moira.web.response

import com.minerbalan.moira.usecase.article.ArticleOutputData

data class ArticleResponse(
    override val isSuccess: Boolean,
    override val message: String,
    val articles: List<ArticleOutputData>,
    val articlesCount: Long
) : BaseResponse
