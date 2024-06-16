package com.example.api.domain.coupon

import org.springframework.stereotype.Service

@Service
class IssueCouponService(
    private val couponAdapter: CouponAdapter
) {
    fun save(couponEventId: Long, userId: Long): Coupon {
        val coupon = Coupon.generate(userId, couponEventId)
        return couponAdapter.save(coupon)
    }
}