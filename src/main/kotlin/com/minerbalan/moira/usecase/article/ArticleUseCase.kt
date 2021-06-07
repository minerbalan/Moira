package com.minerbalan.moira.usecase.article


interface ArticleUseCase {
    fun fetchArticleLatest(email: String, limit: Int, offset: Int): List<ArticleOutputData>

    fun countArticle(email: String): Long
}
