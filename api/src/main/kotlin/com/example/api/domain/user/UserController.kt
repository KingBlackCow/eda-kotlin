package com.example.api.domain.user

import com.example.api.db.user.UserEntity
import com.example.api.db.user.UserFollowEntity
import com.example.api.domain.user.UserService.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {
    @PostMapping
    fun create(@RequestBody request: UserCreateRequest): ResponseEntity<UserResponse> {
        val userEntity: UserEntity = userService.create(UserCreateRequest(request.name, request.email))
        return ResponseEntity.ok().body(toDto(userEntity))
    }

    @PostMapping("/follow")
    fun follow(@RequestBody request: UserFollowRequest): ResponseEntity<Long> {
        val userFollowEntity: UserFollowEntity = userService.follow(request)
        return ResponseEntity.ok().body(userFollowEntity.id)
    }

    @GetMapping("/follow/{id}")
    fun getFollower(@PathVariable id: Long): ResponseEntity<List<Long>> {
        val follower: List<Long> = userService.getFollower(id)
        return ResponseEntity.ok().body(follower)
    }

    @GetMapping("/following/{id}")
    fun getFollowing(@PathVariable id: Long): ResponseEntity<List<Long>> {
        val following: List<Long> = userService.getFollowing(id)
        return ResponseEntity.ok().body(following)
    }

    private fun toDto(userEntity: UserEntity): UserResponse {
        return UserResponse(userEntity.id, userEntity.name, userEntity.email, userEntity.createdAt)
    }

    data class UserResponse(
        val id: Long? = null,
        val name: String? = null,
        val email: String? = null,
        val createdAt: LocalDateTime? = null
    )

}