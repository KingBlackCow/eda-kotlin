package com.example.api.domain.post

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class ResolvedPost(
    @JsonProperty("id") val id: Long,
    @JsonProperty("title") val title: String? = null,
    @JsonProperty("content") val content: String? = null,
    @JsonProperty("userId") val userId: Long? = null,
    @JsonProperty("userName") val userName: String? = null,
    @JsonProperty("categoryId") val categoryId: Long? = null,
    @JsonProperty("categoryName") val categoryName: String? = null,
    @JsonProperty("createdAt") val createdAt: LocalDateTime? = null,
    @JsonProperty("updatedAt") val updatedAt: LocalDateTime? = null,
    @JsonProperty("updated") val updated: Boolean
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