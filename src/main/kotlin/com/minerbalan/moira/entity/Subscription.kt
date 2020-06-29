package com.minerbalan.moira.entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "subscriptions")
data class Subscription(
        @Id
        @Column(name = "id")
        var id: Long,

        @Column(name = "name")
        var name: String,

        @Column(name = "url")
        var url: String,

        @Column(name = "created_at")
        var createdAt: LocalDateTime,

        @Column(name = "last_fetched_at")
        var lastFetchedAt: LocalDateTime,

        @Column(name = "updated_at")
        var updatedAt: LocalDateTime? = null,

        @Column(name = "deleted_at")
        var deletedAt: LocalDateTime? = null
)