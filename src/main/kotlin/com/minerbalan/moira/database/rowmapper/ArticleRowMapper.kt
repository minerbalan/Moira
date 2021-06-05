package com.minerbalan.moira.database.rowmapper

import com.minerbalan.moira.domain.entity.ArticleEntity
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class ArticleRowMapper : RowMapper<ArticleEntity> {
    override fun mapRow(rs: ResultSet, rowNum: Int): ArticleEntity {
        return ArticleEntity(
                id = rs.getLong("id"),
                subscriptionId = rs.getLong("subscription_id"),
                url = rs.getString("url"),
                title = rs.getString("title"),
                description = rs.getString("description"),
                thumbnail = rs.getString("thumbnail"),
                createdAt = rs.getTimestamp("created_at").toLocalDateTime(),
                publishedAt = rs.getTimestamp("published_at").toLocalDateTime()
        )
    }

}
