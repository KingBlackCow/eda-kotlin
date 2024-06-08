package com.example.api.domain.postlist

import com.example.api.domain.post.Post
import org.springframework.stereotype.Component

@Component
class SubscribingPostAdapter(
    val subscribingPostRepository: SubscribingPostRepository
) {
    fun addPostToFollowerInboxes(post: Post, followerUserIds: List<Long>) {
        val documents: MutableList<SubscribingPostDocument> = followerUserIds.stream().map { followerUserId ->
            SubscribingPostDocument.generate(post, followerUserId)
        }.toList()
        subscribingPostRepository.saveAll(documents)
    }

    fun removePostFromFollowerInboxes(postId: Long) {
        subscribingPostRepository.deleteAllByPostId(postId)
    }

    fun listPostIdsByFollowerUserIdWithPagination(followerUserId: Long, pageNumber: Int, pageSize: Int): List<Long> {
        val documents: List<SubscribingPostDocument> =
            subscribingPostRepository.findByFollowerUserIdWithPagination(followerUserId, pageNumber, pageSize)
        return documents.stream().map{ it.postId }.toList()
    }
}