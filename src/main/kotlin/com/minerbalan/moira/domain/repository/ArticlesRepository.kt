package com.minerbalan.moira.domain.repository

import com.minerbalan.moira.domain.entity.Article

interface ArticlesRepository {
    fun findByThumbnailIsNull(): List<Article>

    fun bulkInsertOrIgnoreArticles(articleList: List<Article>)

    fun bulkUpdateThumbnailArticles(articleList: List<Article>)

    fun fetchArticleLatest(email: String, limit: Int, offset: Int): List<Article>
}
