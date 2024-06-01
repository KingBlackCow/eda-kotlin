package com.example.api.adapter

import com.example.api.domain.coupon.CouponEvent
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.core.RedisTemplate
import java.time.Duration

val KEY_PREFIX: String = "coupon_event.v1:"
val EXPIRE_SECONDS: Long = 60 * 2L

class CouponEventCacheAdapter(
    private val objectMapper: ObjectMapper = ObjectMapper(),
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun set(couponEvent: CouponEvent) {
        val jsonString: String
        try {
            jsonString = objectMapper.writeValueAsString(couponEvent)
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }
        redisTemplate.opsForValue().set(
            this.generateCacheKey(couponEvent.id!!),
            jsonString,
            Duration.ofSeconds(EXPIRE_SECONDS)
        )
    }

    fun get(couponEventId: Long): CouponEvent? {
        val jsonString: String = redisTemplate.opsForValue().get(this.generateCacheKey(couponEventId))
            ?: return null
        try {
            return objectMapper.readValue(jsonString, CouponEvent::class.java)
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }
    }

    private fun generateCacheKey(couponEventId: Long): String {
        return KEY_PREFIX + couponEventId
    }
}