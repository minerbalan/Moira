package com.minerbalan.moira.database.rowmapper

import com.minerbalan.moira.domain.entity.Subscription
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class SubscriptionRowMapper : RowMapper<Subscription> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Subscription {
        return Subscription(
                id = rs.getLong("id"),
                name = rs.getString("name"),
                url = rs.getString("url"),
                createdAt = rs.getTimestamp("created_at").toLocalDateTime(),
                lastFetchedAt = rs.getTimestamp("last_fetched_at").toLocalDateTime(),
                updatedAt = rs.getTimestamp("updated_at")?.toLocalDateTime(),
                deletedAt = rs.getTimestamp("deleted_at")?.toLocalDateTime()
        )
    }
}
