package com.example.api.domain.post.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class PostCreateRequest(
    @JsonProperty("title") val title: String,
    @JsonProperty("userId") val userId: Long,
    @JsonProperty("content") val content: String,
    @JsonProperty("categoryId") val categoryId: Long
)

data class PostUpdateRequest(
    val title: String,
    val content: String,
    val categoryId: Long
)