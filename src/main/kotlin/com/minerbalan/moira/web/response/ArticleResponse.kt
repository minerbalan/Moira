package com.minerbalan.moira.web.response

import com.minerbalan.moira.domain.entity.ArticleEntity

data class ArticleResponse(
    override val isSuccess: Boolean,
    override val message: String,
    val articleEntities: List<ArticleEntity>,
    val articlesCount: Long
) : BaseResponse
