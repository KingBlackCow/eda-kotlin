package com.example.api.domain.subscribingpost

import com.example.api.domain.post.Post
import com.example.api.domain.user.UserService
import org.springframework.stereotype.Service

@Service
class SubscribingPostAddToInboxService(
    private val subscribingPostAdapter: SubscribingPostAdapter,
    private val userService: UserService
) {

    fun saveSubscribingInboxPost(post: Post) {
        val followerUserIds: List<Long> = userService.getFollower(post.userId)
        subscribingPostAdapter.addPostToFollowerInboxes(post, followerUserIds)
    }
}