package com.minerbalan.moira.gateway.rss

import com.minerbalan.moira.domain.repository.article.InsertArticleData
import java.time.LocalDateTime

data class RssArticleOutputData(
    var subscriptionId: Long,

    var url: String,

    var title: String,

    var description: String?,

    var thumbnail: String? = null,

    var createdAt: LocalDateTime,

    var publishedAt: LocalDateTime
)

fun RssArticleOutputData.toInsertArticleData(): InsertArticleData {
    return InsertArticleData(
        subscriptionId = this.subscriptionId,
        url = this.url,
        title = this.title,
        description = this.description,
        thumbnail = this.thumbnail,
        createdAt = this.createdAt,
        publishedAt = this.publishedAt
    )
}
