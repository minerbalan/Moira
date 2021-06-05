package com.minerbalan.moira.usecase

import com.minerbalan.moira.domain.entity.ArticleEntity

interface ArticleUseCase {
    fun fetchArticleLatest(email: String, limit: Int, offset: Int): List<ArticleEntity>

    fun countArticle(email: String): Long
}
