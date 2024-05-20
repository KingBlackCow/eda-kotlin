package com.example.db.coupon

import org.springframework.data.jpa.repository.JpaRepository

interface CouponEventJpaRepository : JpaRepository<CouponEventEntity, Long> {
}