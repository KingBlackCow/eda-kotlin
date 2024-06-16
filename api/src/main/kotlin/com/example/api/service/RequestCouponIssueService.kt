package com.example.api.service

import com.example.api.domain.coupon.CouponIssueRequestAdapter
import org.springframework.stereotype.Service

@Service
class RequestCouponIssueService(
    private val couponIssueRequestAdapter: CouponIssueRequestAdapter
) {

    fun queue(couponEventId: Long, userId: Long) {
        couponIssueRequestAdapter.sendMessage(userId, couponEventId)
    }
}