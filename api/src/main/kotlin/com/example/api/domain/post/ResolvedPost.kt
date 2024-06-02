package com.example.api.domain.post

import java.time.LocalDateTime

data class ResolvedPost(
    val id: Long,
    val title: String? = null,
    val content: String? = null,
    val userId: Long? = null,
    val userName: String? = null,
    val categoryId: Long? = null,
    val categoryName: String? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val updated: Boolean
) {
    companion object {
        fun generate(
            post: Post,
            userName: String?,
            categoryName: String?
        ): ResolvedPost {
            return ResolvedPost(
                post.id!!,
                post.title,
                post.content,
                post.userId,
                userName!!,
                post.categoryId,
                categoryName!!,
                post.createdAt,
                post.updatedAt,
                !post.createdAt?.equals(post.updatedAt)!!
            )
        }
    }
}