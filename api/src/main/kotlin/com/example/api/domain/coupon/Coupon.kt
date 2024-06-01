package com.example.api.domain.coupon

import java.time.LocalDateTime

class Coupon(
    val id: Long? = null,
    val userId: Long? = null,
    val couponEventId: Long? = null,
    val issuedAt: LocalDateTime? = null,
    var usedAt: LocalDateTime? = null
) {
    fun use(): Coupon {
        this.usedAt = LocalDateTime.now()
        return this
    }

    companion object {
        fun generate(userId: Long, couponEventId: Long): Coupon {
            return Coupon(null, userId, couponEventId, LocalDateTime.now(), null)
        }
    }
}
