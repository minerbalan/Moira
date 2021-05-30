package com.minerbalan.moira.web.response

import com.minerbalan.moira.domain.entity.Article

data class ArticleResponse(
    override val isSuccess: Boolean,
    override val message: String,
    val articles: List<Article>,
    val articlesCount: Long
) : BaseResponse
