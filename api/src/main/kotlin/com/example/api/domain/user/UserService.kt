package com.example.api.domain.user

import com.example.api.db.user.UserEntity
import com.example.api.db.user.UserFollowEntity
import com.example.api.db.user.UserFollowJpaRepository
import com.example.api.db.user.UserJpaRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserJpaRepository,
    private val userFollowJpaRepository: UserFollowJpaRepository
) {
    @Transactional
    fun create(request: UserCreateRequest): UserEntity {
        val user = User.generate(request.name!!, request.email!!)
        return userRepository.save(UserEntity.fromUser(user))
    }

    @Transactional
    fun follow(request: UserFollowRequest): UserFollowEntity {
        val following: UserEntity = userRepository.findById(request.followingId!!).orElseThrow()
        val follower: UserEntity = userRepository.findById(request.followerId!!).orElseThrow()
        val userFollowEntity = UserFollowEntity(following, follower)
        return userFollowJpaRepository.save(userFollowEntity)
    }

    @Transactional
    fun getFollower(id: Long): List<Long> {
        return userFollowJpaRepository.findByFollowingId(id).stream().map { it.follower.id!! }.toList()
    }

    fun getFollowing(id: Long): List<Long> {
        return userFollowJpaRepository.findByFollowerId(id).stream().map { it.following.id!! }.toList()
    }

    data class UserCreateRequest(
        val name: String? = null,
        val email: String? = null
    )

    data class UserFollowRequest(
        val followingId: Long? = null,
        val followerId: Long? = null
    )
}