package com.minerbalan.moira.domain.repository.article

import com.minerbalan.moira.domain.entity.ArticleEntity

interface ArticlesRepository {
    fun findByThumbnailIsNull(): List<ArticleEntity>

    fun bulkInsertOrIgnoreArticles(insertArticleList: List<InsertArticleData>)

    fun bulkUpdateThumbnailArticles(articleEntityList: List<ArticleEntity>)

    fun fetchArticleLatest(email: String, limit: Int, offset: Int): List<ArticleData>

    fun countArticle(email: String): Long
}
