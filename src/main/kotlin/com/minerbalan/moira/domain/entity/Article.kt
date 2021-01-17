package com.minerbalan.moira.domain.entity

import org.hibernate.annotations.Loader
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Loader
@Table(name = "articles")
data class Article(
        @Id
        @Column(name = "id")
        var id: Long? = null,

        @Column(name = "subscription_id")
        var subscriptionId: Long,

        @Column(name = "url")
        var url: String? = null,

        @Column(name = "title")
        var title: String? = null,

        @Column(name = "description")
        var description: String? = null,

        @Column(name = "thumbnail")
        var thumbnail: String? = null,

        @Column(name = "created_at")
        var createdAt: LocalDateTime? = null,

        @Column(name = "published_at")
        var publishedAt: LocalDateTime? = null
)
