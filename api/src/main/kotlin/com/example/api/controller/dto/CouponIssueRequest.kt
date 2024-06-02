package com.example.api.controller.dto

data class CouponIssueRequest(
    val userId: Long,
    val couponEventId: Long
)