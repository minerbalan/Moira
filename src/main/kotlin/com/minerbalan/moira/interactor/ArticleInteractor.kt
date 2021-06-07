package com.minerbalan.moira.interactor

import com.minerbalan.moira.domain.repository.article.ArticlesRepository
import com.minerbalan.moira.usecase.article.ArticleOutputData
import com.minerbalan.moira.usecase.article.ArticleUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ArticleInteractor(private val articlesRepository: ArticlesRepository) : ArticleUseCase {
    override fun fetchArticleLatest(email: String, limit: Int, offset: Int): List<ArticleOutputData> {
        val articleEntityList = articlesRepository.fetchArticleLatest(email, limit, offset)
        val result = ArrayList<ArticleOutputData>()
        articleEntityList.forEach {
            result.add(
                ArticleOutputData(
                    id = it.id,
                    title = it.title,
                    url = it.url,
                    description = it.description,
                    thumbnail = it.thumbnail
                )
            )
        }
        return result
    }

    override fun countArticle(email: String): Long {
        return articlesRepository.countArticle(email)
    }
}
