package com.example.api.domain.postlist

import java.time.LocalDateTime


data class PostInListDto(
    val id: Long? = null,
    val title: String? = null,
    val userName: String? = null,
    val createdAt: LocalDateTime? = null
)