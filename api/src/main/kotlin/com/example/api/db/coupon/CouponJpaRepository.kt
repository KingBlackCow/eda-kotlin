package com.example.api.db.coupon

import org.springframework.data.jpa.repository.JpaRepository

interface CouponJpaRepository : JpaRepository<CouponEntity, Long> {
    fun findAllByUserId(userId: Long): List<CouponEntity>
}