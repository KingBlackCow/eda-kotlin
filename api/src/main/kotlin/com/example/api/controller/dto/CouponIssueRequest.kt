package com.example.api.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CouponIssueRequest(
    @JsonProperty("userId")
    val userId: Long,
    @JsonProperty("couponEventId")
    val couponEventId: Long
)