package com.example.db.user

import com.example.db.common.BaseEntity
import com.example.domain.user.User
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