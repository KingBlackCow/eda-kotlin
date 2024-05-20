package com.example.db.coupon

import com.example.domain.coupon.Coupon
import com.example.domain.coupon.CouponEvent
import com.example.domain.coupon.ResolvedCoupon

object CouponEntityConverter {
    fun toCouponEntity(coupon: Coupon): CouponEntity {
        return CouponEntity(
            id = coupon.id,
            userId = coupon.userId,
            couponEventId = coupon.couponEventId,
            issuedAt = coupon.issuedAt,
            usedAt = coupon.usedAt,
            couponEvent = null
        )
    }

    fun toCouponModel(couponEntity: CouponEntity): Coupon {
        return Coupon(
            couponEntity.id,
            couponEntity.userId,
            couponEntity.couponEventId,
            couponEntity.issuedAt,
            couponEntity.usedAt
        )
    }

    fun toCouponEventModel(couponEventEntity: CouponEventEntity): CouponEvent {
        return CouponEvent(
            couponEventEntity.id,
            couponEventEntity.displayName,
            couponEventEntity.expiresAt,
            couponEventEntity.issueLimit
        )
    }

    fun toResolvedCouponModel(couponEntity: CouponEntity): ResolvedCoupon {
        return ResolvedCoupon(
            toCouponModel(couponEntity),
            toCouponEventModel(couponEntity.couponEvent!!)
        )
    }
}
