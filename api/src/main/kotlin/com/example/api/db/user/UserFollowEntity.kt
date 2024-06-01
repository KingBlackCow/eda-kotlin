package com.example.api.db.user

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
@Table(name = "user_follow")
@EntityListeners(AuditingEntityListener::class)
class UserFollowEntity(
    @JoinColumn(name = "follower_id")
    @ManyToOne
    private val follower: UserEntity,

    @JoinColumn(name = "following_id")
    @ManyToOne
    private val following: UserEntity
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private var createdAt: LocalDateTime? = null
}