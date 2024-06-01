package com.example.api.db.coupon

import com.example.api.domain.coupon.CouponEvent
import org.springframework.stereotype.Component

@Component
class CouponEventAdapter(
    val couponEventJpaRepository: CouponEventJpaRepository
) {

    fun findById(id: Long): CouponEvent? {
        val couponEventEntity = couponEventJpaRepository.findById(id).orElse(null) ?: return null
        return toModel(couponEventEntity)
    }

    private fun toModel(couponEventEntity: CouponEventEntity): CouponEvent {
        return CouponEvent(
            couponEventEntity.id,
            couponEventEntity.displayName,
            couponEventEntity.expiresAt,
            couponEventEntity.issueLimit
        )
    }
}
