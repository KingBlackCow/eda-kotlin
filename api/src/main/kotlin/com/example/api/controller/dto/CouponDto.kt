package com.example.api.controller.dto

import java.time.LocalDateTime

data class CouponDto(
    val id: Long,
    val displayName: String?,
    val expiresAt: LocalDateTime?
)