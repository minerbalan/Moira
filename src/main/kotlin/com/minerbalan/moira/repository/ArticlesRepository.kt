package com.minerbalan.moira.repository

import com.minerbalan.moira.entity.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticlesRepository : JpaRepository<Article?, Long?> {
    fun findByThumbnailIsNull(): List<Article?>
}