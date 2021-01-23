package com.minerbalan.moira.domain.repository

import com.minerbalan.moira.domain.entity.Article
import org.springframework.stereotype.Repository

@Repository
interface ArticlesRepository {
    fun findByThumbnailIsNull(): List<Article>

    fun bulkInsertOrIgnoreArticles(articleList: List<Article>)

    fun bulkUpdateThumbnailArticles(articleList: List<Article>)
}