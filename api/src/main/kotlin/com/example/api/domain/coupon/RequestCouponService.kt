package com.example.api.domain.coupon

import org.springframework.stereotype.Service

@Service
class RequestCouponService(
    private val couponIssueRequestAdapter: CouponIssueRequestAdapter,
) {
    fun queue(couponEventId: Long, userId: Long) {
        couponIssueRequestAdapter.sendMessage(userId, couponEventId)
    }
}