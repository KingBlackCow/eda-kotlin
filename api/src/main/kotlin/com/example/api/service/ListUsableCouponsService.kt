package com.example.api.service

import com.example.api.db.coupon.CouponAdapter
import com.example.api.domain.coupon.ResolvedCoupon
import org.springframework.stereotype.Service

@Service
class ListUsableCouponsService(
    private val couponAdapter: CouponAdapter
) {
    fun listByUserId(userId: Long): List<ResolvedCoupon> {
        val resolvedCoupons: List<ResolvedCoupon> = couponAdapter.listByUserId(userId)
        return resolvedCoupons.stream().filter(ResolvedCoupon::canBeUsed).toList()
    }
}