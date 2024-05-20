package com.example.db.coupon

import com.example.domain.coupon.Coupon
import com.example.domain.coupon.ResolvedCoupon
import org.springframework.stereotype.Component

@Component
class CouponAdapter(
    val couponJpaRepository: CouponJpaRepository
) {

    fun save(coupon: Coupon): Coupon {
        val couponEntity = couponJpaRepository.save(CouponEntityConverter.toCouponEntity(coupon))
        return CouponEntityConverter.toCouponModel(couponEntity)
    }

    fun listByUserId(userId: Long): List<ResolvedCoupon> {
        val couponEntities: List<CouponEntity> = couponJpaRepository.findAllByUserId(userId)
        return couponEntities.stream().map { CouponEntityConverter.toResolvedCouponModel(it) }.toList()
    }
}
