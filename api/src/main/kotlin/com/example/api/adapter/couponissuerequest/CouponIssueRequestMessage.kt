package com.example.api.adapter.couponissuerequest


data class CouponIssueRequestMessage(
    val userId: Long,
    val couponEventId: Long
) {

    companion object {
        fun from(userId: Long, couponEventId: Long): CouponIssueRequestMessage {
            return CouponIssueRequestMessage(userId, couponEventId)
        }
    }
}
