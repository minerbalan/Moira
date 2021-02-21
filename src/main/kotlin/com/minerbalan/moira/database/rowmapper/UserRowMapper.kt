package com.minerbalan.moira.database.rowmapper

import com.minerbalan.moira.domain.entity.User
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class UserRowMapper : RowMapper<User> {
    override fun mapRow(rs: ResultSet, rowNum: Int): User? {
        return User(
                id = rs.getLong("id"),
                userName = rs.getString("user_name"),
                email = rs.getString("email"),
                password = rs.getString("password"),
                createdAt = rs.getTimestamp("created_at").toLocalDateTime(),
                updatedAt = rs.getTimestamp("updated_at")?.toLocalDateTime()
        )
    }
}
