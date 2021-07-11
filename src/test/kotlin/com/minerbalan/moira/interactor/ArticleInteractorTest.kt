package com.minerbalan.moira.interactor

import com.minerbalan.moira.domain.repository.article.ArticleData
import com.minerbalan.moira.domain.repository.article.ArticlesRepository
import com.minerbalan.moira.usecase.article.ArticleOutputData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
@DisplayName("ArticleInteractor")
class ArticleInteractorTest {
    private val testEmail = "test@example.com"

    @Test
    fun testFetchArticle() {
        val testCount = 30
        val offset = 0
        val exportArticleList = ArrayList<ArticleData>()
        val now = LocalDateTime.now()
        exportArticleList.add(
            ArticleData(
                id = 1,
                subscriptionId = 1,
                url = "example.com/1",
                title = "テスト1",
                description = "説明",
                thumbnail = "example.com/1/thumbnail",
                publishedAt = now,
                subscriptionName = "購読先名"
            )
        )
        val mockArticlesRepository = mock<ArticlesRepository> {
            on { fetchArticleLatest(testEmail, testCount, offset) } doReturn (exportArticleList)
        }
        val outputArticleData = ArticleOutputData(
            id = 1,
            url = "example.com/1",
            title = "テスト1",
            description = "説明",
            thumbnail = "example.com/1/thumbnail",
            subscriptionName = "購読先名",
            publishedAt = now
        )

        val articleInteractor = ArticleInteractor(mockArticlesRepository)
        assertThat(articleInteractor.fetchArticleLatest(testEmail, testCount, offset)).containsOnly(outputArticleData)
    }

    @Test
    fun testCountArticle() {
        val testCount: Long = 123
        val mockArticlesRepository = mock<ArticlesRepository> {
            on { countArticle(testEmail) } doReturn testCount
        }
        val articleInteractor = ArticleInteractor(mockArticlesRepository)
        assertThat(articleInteractor.countArticle(testEmail)).isEqualTo(testCount)
    }
}
