package com.example.api.adapter.couponissuerequest

import com.fasterxml.jackson.annotation.JsonProperty


data class CouponIssueRequestMessage(
    @JsonProperty("userId")
    val userId: Long,
    @JsonProperty("couponEventId")
    val couponEventId: Long
) {

    companion object {
        fun from(userId: Long, couponEventId: Long): CouponIssueRequestMessage {
            return CouponIssueRequestMessage(userId, couponEventId)
        }
    }
}
