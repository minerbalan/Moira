package com.minerbalan.moira.domain.repository

import com.minerbalan.moira.domain.entity.ArticleEntity

interface ArticlesRepository {
    fun findByThumbnailIsNull(): List<ArticleEntity>

    fun bulkInsertOrIgnoreArticles(articleEntityList: List<ArticleEntity>)

    fun bulkUpdateThumbnailArticles(articleEntityList: List<ArticleEntity>)

    fun fetchArticleLatest(email: String, limit: Int, offset: Int): List<ArticleEntity>

    fun countArticle(email: String): Long
}
