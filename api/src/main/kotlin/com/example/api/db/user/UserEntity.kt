package com.example.api.db.user

import com.example.api.db.common.BaseEntity
import com.example.api.domain.user.User
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity(name = "user")
class UserEntity(
    private val name: String,
    private val email: String
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null
    private val deletedAt: LocalDateTime? = null

    companion object {
        fun fromUser(user: User): UserEntity {
            return UserEntity(user.name, user.email)
        }
    }
}