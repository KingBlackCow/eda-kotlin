package com.example.api.domain.postlist

import com.example.api.domain.post.PostResolvingHelpService
import com.example.api.domain.post.ResolvedPost
import org.springframework.stereotype.Service

private const val PAGE_SIZE = 5

@Service
class SubscribingPostListService(
    private val subscribingPostAdapter: SubscribingPostAdapter,
    private val postResolvingHelpService: PostResolvingHelpService
) {
    fun listSubscribingInboxPosts(request: Request): List<ResolvedPost> {
        val subscribingPostIds = subscribingPostAdapter.listPostIdsByFollowerUserIdWithPagination(
            request.followerUserId,
            request.pageNumber,
            PAGE_SIZE
        )
        return postResolvingHelpService.resolvePostsByIds(subscribingPostIds)
    }
    data class Request(
        val pageNumber: Int = 0,
        val followerUserId: Long
    )
}