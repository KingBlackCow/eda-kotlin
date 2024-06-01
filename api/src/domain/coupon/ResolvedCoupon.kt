package com.example.domain.coupon

class ResolvedCoupon(
    val coupon: Coupon,
    val couponEvent: CouponEvent,
) {

    fun canBeUsed(): Boolean {
        return !couponEvent.isExpired && coupon.usedAt == null
    }

    companion object {
        fun generate(coupon: Coupon, couponEvent: CouponEvent): ResolvedCoupon {
            return ResolvedCoupon(coupon, couponEvent)
        }
    }
}