package com.example.api.domain.post.dto

import java.time.LocalDateTime

data class PostDto(
    val id: Long? = null,
    val title: String? = null,
    val content: String? = null,
    val userId: Long? = null,
    val categoryId: Long? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
)

data class PostDetailDto(
    val id: Long? = null,
    val title: String? = null,
    val content: String? = null,
    val userName: String? = null,
    val categoryName: String? = null,
    val createdAt: LocalDateTime? = null,
    val updated:Boolean = false
)