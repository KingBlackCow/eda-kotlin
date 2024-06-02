package com.example.api.domain.post

import com.example.api.domain.post.dto.PostCreateRequest
import com.example.api.domain.post.dto.PostDetailDto
import com.example.api.domain.post.dto.PostDto
import com.example.api.domain.post.dto.PostUpdateRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts")
class PostController(
    private val postService: PostService
) {
    @PostMapping
    fun createPost(@RequestBody request: PostCreateRequest): ResponseEntity<PostDto> {
        val post: Post =
            postService.create(CreateRequest(request.userId, request.title, request.content, request.categoryId))
        return ResponseEntity.ok().body(toDto(post))
    }

    @PutMapping("/{postId}")
    fun updatePost(@PathVariable("postId") id: Long, @RequestBody request: PostUpdateRequest): ResponseEntity<PostDto> {
        val post: Post = postService.update(UpdateRequest(id, request.title, request.content, request.categoryId))
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok().body(toDto(post))
    }

    @DeleteMapping("/{postId}")
    fun deletePost(@PathVariable("postId") id: Long): ResponseEntity<PostDto> {
        val post: Post = postService.delete(DeleteRequest(id))
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok().body(toDto(post))
    }

    @GetMapping("/{postId}/detail")
    fun readPostDetail(@PathVariable("postId") id: Long): ResponseEntity<PostDetailDto> {
        val resolvedPost: ResolvedPost = postService.getById(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok().body(toDto(resolvedPost))
    }

    private fun toDto(post: Post): PostDto {
        return PostDto(
            post.id,
            post.title,
            post.content,
            post.userId,
            post.categoryId,
            post.createdAt,
            post.updatedAt,
            post.deletedAt
        )
    }

    private fun toDto(resolvedPost: ResolvedPost): PostDetailDto {
        return PostDetailDto(
            resolvedPost.id,
            resolvedPost.title,
            resolvedPost.content,
            resolvedPost.userName,
            resolvedPost.categoryName,
            resolvedPost.createdAt,
            resolvedPost.updated
        )
    }
}