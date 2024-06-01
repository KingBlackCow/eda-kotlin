package com.example.api.domain.user

import java.time.LocalDateTime

class User(
    val id: Long? = null,
    val name: String,
    val email: String,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    companion object {
        fun generate(
            name: String,
            email: String
        ): User {
            val now = LocalDateTime.now()
            return User(null, name, email, now, now, null)
        }
    }
}