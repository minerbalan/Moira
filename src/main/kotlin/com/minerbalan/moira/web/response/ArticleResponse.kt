package com.minerbalan.moira.web.response

import com.minerbalan.moira.domain.entity.Article

data class ArticleResponse(
    val articles: List<Article>
)
