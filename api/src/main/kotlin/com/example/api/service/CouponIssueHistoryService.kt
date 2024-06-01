package com.example.api.service

import com.example.api.adapter.CouponEventAdapter
import com.example.api.adapter.CouponEventCacheAdapter
import com.example.api.adapter.CouponIssueRequestHistoryAdapter
import com.example.api.domain.coupon.CouponEvent

class CouponIssueHistoryService(
    private val couponIssueRequestHistoryAdapter: CouponIssueRequestHistoryAdapter,
    private val couponEventAdapter: CouponEventAdapter,
    private val couponEventCacheAdapter: CouponEventCacheAdapter,
) {
    fun isFirstRequestFromUser(couponEventId: Long?, userId: Long?): Boolean {
        return couponIssueRequestHistoryAdapter.setHistoryIfNotExists(couponEventId!!, userId!!)
    }

    fun hasRemainingCoupon(couponEventId: Long): Boolean {
        val couponEvent = this.getCouponEventById(couponEventId)
        return couponIssueRequestHistoryAdapter.getRequestSequentialNumber(couponEventId) <= couponEvent.issueLimit
    }

    private fun getCouponEventById(couponEventId: Long): CouponEvent {
        val couponEventCache = couponEventCacheAdapter.get(couponEventId)
        if (couponEventCache != null) {
            return couponEventCache
        } else {
            val couponEvent = couponEventAdapter.findById(couponEventId)
                ?: throw RuntimeException("Coupon event does not exist.")
            couponEventCacheAdapter.set(couponEvent)
            return couponEvent
        }
    }
}