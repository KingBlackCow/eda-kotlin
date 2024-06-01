package com.example.api.domain.coupon

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime

class CouponEvent(
    val id: Long? = null,
    val displayName: String? = null,
    val expiresAt: LocalDateTime? = null,
    val issueLimit: Long? = null
) {

    @get:JsonIgnore
    val isExpired: Boolean
        get() = expiresAt!!.isBefore(LocalDateTime.now())

    companion object {
        fun generate(
            displayName: String,
            expiresAt: LocalDateTime,
            issueLimit: Long
        ): CouponEvent {
            return CouponEvent(null, displayName, expiresAt, issueLimit)
        }
    }
}
