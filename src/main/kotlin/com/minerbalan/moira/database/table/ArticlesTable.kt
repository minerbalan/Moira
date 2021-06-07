package com.minerbalan.moira.database.table

import com.minerbalan.moira.domain.entity.ArticleEntity
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.`java-time`.datetime


object ArticlesTable : IdTable<Long>("articles") {
    override val id = long("id").autoIncrement().entityId()
    val subscriptionId = reference(
        "subscription_id",
        SubscriptionsTable.id,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
    val url = varchar("url", 255)
    val title = varchar("title", 1024)
    val description = text("description").nullable()
    val thumbnail = varchar("thumbnail", 1024).nullable()
    val createdAt = datetime("created_at")
    val publishedAt = datetime("published_at")

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(subscriptionId, url)
    }
}


fun ResultRow.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        id = this[ArticlesTable.id].value,
        subscriptionId = this[ArticlesTable.subscriptionId],
        url = this[ArticlesTable.url],
        title = this[ArticlesTable.title],
        description = this[ArticlesTable.description],
        thumbnail = this[ArticlesTable.thumbnail],
        createdAt = this[ArticlesTable.createdAt],
        publishedAt = this[ArticlesTable.publishedAt]
    )
}
