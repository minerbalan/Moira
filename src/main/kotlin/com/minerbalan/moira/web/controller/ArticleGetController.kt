package com.minerbalan.moira.web.controller

import com.minerbalan.moira.usecase.article.ArticleUseCase
import com.minerbalan.moira.web.request.ArticleGetRequest
import com.minerbalan.moira.web.response.ArticleResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleGetController(private val articleUseCase: ArticleUseCase) {

    @GetMapping("/articles")
    fun getArticle(
        @AuthenticationPrincipal user: UserDetails?,
        @Validated request: ArticleGetRequest
    ): ArticleResponse {
        if (user == null) {
            throw IllegalStateException()
        }

        val limit = request.limit ?: 50
        val offset = request.offset ?: 0

        val result = articleUseCase.fetchArticleLatest(user.username, limit, offset)
        val articleCount = articleUseCase.countArticle(user.username)
        return ArticleResponse(true, "success", result, articleCount)
    }
}
