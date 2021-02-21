package com.minerbalan.moira.database

import com.minerbalan.moira.database.rowmapper.UserRowMapper
import com.minerbalan.moira.domain.entity.User
import com.minerbalan.moira.domain.repository.UsersRepository
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.lang.Exception

@Repository
class UsersRepositoryImpl(private val jdbcTemplate: JdbcTemplate) : UsersRepository {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun createUser(user: User) {
        val sql = "INSERT INTO users(user_name, email, password, created_at) VALUES (?,?,?,?)"
        jdbcTemplate.update(sql, user.userName, user.email, user.password, user.createdAt)
    }

    override fun existsUser(email: String): Boolean {
        val sql = "SELECT * FROM users WHERE email = ?"
        val userList = jdbcTemplate.query(sql, UserRowMapper(), email)
        return userList.isNotEmpty()
    }

    override fun findUserFromEmail(email: String): User? {
        val sql = "SELECT * FROM users WHERE email = ?"
        try {
            val userList = jdbcTemplate.query(sql, UserRowMapper(), email)
            if (userList.isNotEmpty()) {
                return userList[0]
            }
        } catch (e: Exception) {
            logger.error("An error has occurred on find user from email", e)
        }
        return null
    }
}
