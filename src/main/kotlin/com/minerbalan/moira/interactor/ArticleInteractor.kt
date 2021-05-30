package com.minerbalan.moira.interactor

import com.minerbalan.moira.domain.entity.Article
import com.minerbalan.moira.domain.repository.ArticlesRepository
import com.minerbalan.moira.usecase.ArticleUseCase
import org.springframework.stereotype.Service

@Service
class ArticleInteractor(private val articlesRepository: ArticlesRepository) : ArticleUseCase {
    override fun fetchArticleLatest(email: String, limit: Int, offset: Int): List<Article> {
        return articlesRepository.fetchArticleLatest(email, limit, offset)
    }

    override fun countArticle(email: String): Long {
        return articlesRepository.countArticle(email)
    }
}
