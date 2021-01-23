package com.minerbalan.moira.database

import com.minerbalan.moira.database.rowmapper.ArticleRowMapper
import com.minerbalan.moira.domain.entity.Article
import com.minerbalan.moira.domain.repository.ArticlesRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class ArticlesRepositoryImpl(private val jdbcTemplate: JdbcTemplate) : ArticlesRepository {
    override fun findByThumbnailIsNull(): List<Article> {
        TODO("Not yet implemented")
    }

    override fun bulkInsertOrIgnoreArticles(articleList: List<Article>) {
        TODO("Not yet implemented")
    }

    override fun bulkUpdateThumbnailArticles(articleList: List<Article>) {
        TODO("Not yet implemented")
    }
}
