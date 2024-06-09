package com.example.api.db.user

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface UserFollowJpaRepository : JpaRepository<UserFollowEntity, Long> {
    @EntityGraph(attributePaths = ["follower", "following"])
    fun findByFollowerId(followingId: Long): List<UserFollowEntity>

    @EntityGraph(attributePaths = ["follower", "following"])
    fun findByFollowingId(followerId: Long): List<UserFollowEntity>
}