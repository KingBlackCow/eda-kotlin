package com.example.db.coupon

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity(name = "coupon_event")
class CouponEventEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val displayName: String? = null,
    val expiresAt: LocalDateTime? = null,
    val issueLimit: Long? = null
) {






}
