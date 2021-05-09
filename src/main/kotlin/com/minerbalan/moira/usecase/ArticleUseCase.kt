package com.minerbalan.moira.usecase

import com.minerbalan.moira.domain.entity.Article

interface ArticleUseCase {
    fun fetchArticleLatest(email: String, limit: Int, offset: Int): List<Article>
}
