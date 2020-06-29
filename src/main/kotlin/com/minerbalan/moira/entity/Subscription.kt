package com.minerbalan.moira.entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "subscriptions")
class Subscription {
    @Id
    @Column(name = "id")
    var id: Long = 0

    @Column(name = "name")
    var name: String? = null

    @Column(name = "url")
    var url: String? = null

    @Column(name = "created_at")
    var createdAt: LocalDateTime? = null

    @Column(name = "last_fetched_at")
    var lastFetchedAt: LocalDateTime? = null

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
}