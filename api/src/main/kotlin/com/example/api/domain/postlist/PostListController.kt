package com.example.api.domain.postlist

import com.example.api.domain.post.ResolvedPost
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.function.Function

@RestController
@RequestMapping("/list")
class PostListController(
    private val subscribingPostListService: SubscribingPostListService,
    private val postSearchService: PostSearchService,
) {
    @GetMapping("/inbox/{userId}")
    fun listSubscribingPosts(
        @PathVariable("userId") userId: Long,
        @RequestParam(name = "page") page: Int = 0
    ): ResponseEntity<List<PostInListDto>> {
        val subscribingInboxPosts: List<ResolvedPost> = subscribingPostListService.listSubscribingInboxPosts(
            SubscribingPostListService.Request(page, userId)
        )
        return ResponseEntity.ok()
            .body(subscribingInboxPosts.stream().map { resolvedPost: ResolvedPost ->
                this.toDto(resolvedPost) }.toList())
    }

    @GetMapping("/search")
    fun searchPosts(
        @RequestParam("keyword") keyword: String?,
        @RequestParam("page") page: Int
    ): ResponseEntity<List<PostInListDto>> {
        val searchedPosts: List<ResolvedPost> = postSearchService.getSearchResultByKeyword(keyword, page)
        return ResponseEntity.ok()
            .body(searchedPosts.stream().map { resolvedPost: ResolvedPost -> this.toDto(resolvedPost) }.toList())
    }

    private fun toDto(resolvedPost: ResolvedPost): PostInListDto {
        return PostInListDto(
            resolvedPost.id,
            resolvedPost.title,
            resolvedPost.userName,
            resolvedPost.createdAt
        )
    }
}