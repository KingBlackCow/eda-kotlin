package com.example.api.domain.post.dto

data class PostCreateRequest(
    val title: String,
    val userId: Long,
    val content: String,
    val categoryId: Long
)

data class PostUpdateRequest(
    val title: String,
    val content: String,
    val categoryId: Long
)