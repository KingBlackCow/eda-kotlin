package com.example.api.domain.coupon

import com.example.api.db.coupon.CouponEntity
import com.example.api.db.coupon.CouponEntityConverter
import com.example.api.db.coupon.CouponJpaRepository
import org.springframework.stereotype.Component

@Component
class CouponAdapter(
    private val couponRepository: CouponJpaRepository
) {
    fun save(coupon: Coupon): Coupon {
        val couponEntity: CouponEntity = couponRepository.save(CouponEntityConverter.toCouponEntity(coupon))
        return CouponEntityConverter.toCouponModel(couponEntity)
    }

    fun listByUserId(userId: Long): List<ResolvedCoupon> {
        val couponEntities: List<CouponEntity> = couponRepository.findAllByUserId(userId)
        return couponEntities.stream().map(CouponEntityConverter::toResolvedCouponModel).toList()
    }
}