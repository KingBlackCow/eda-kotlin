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
    val follower: UserEntity,

    @JoinColumn(name = "following_id")
    @ManyToOne
    val following: UserEntity
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null
}