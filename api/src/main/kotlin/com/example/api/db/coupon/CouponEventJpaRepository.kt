package com.example.api.db.coupon

import org.springframework.data.jpa.repository.JpaRepository

interface CouponEventJpaRepository : JpaRepository<CouponEventEntity, Long> {
}