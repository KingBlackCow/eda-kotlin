package com.example.api.domain.coupon

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

class CouponEvent(
    @JsonProperty("id")
    val id: Long? = null,
    @JsonProperty("displayName")
    val displayName: String? = null,
    @JsonProperty("expiresAt")
    val expiresAt: LocalDateTime? = null,
    @JsonProperty("issueLimit")
    val issueLimit: Long
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
