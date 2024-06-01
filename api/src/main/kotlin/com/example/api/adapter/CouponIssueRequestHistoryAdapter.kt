package com.example.api.adapter

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class CouponIssueRequestHistoryAdapter(
    private val redisTemplate: RedisTemplate<String, String>
) {
    companion object {
        private const val USER_REQUEST_HISTORY_KEY_PREFIX = "coupon_history.user_request.v1:"
        private const val REQUEST_COUNT_HISTORY_KEY_PREFIX = "coupon_history.request_count.v1:"
        private const val EXPIRE_SECONDS = 60 * 60 * 24 * 7L // 일주일
    }

    fun setHistoryIfNotExists(couponEventId: Long, userId: Long): Boolean {
        return java.lang.Boolean.TRUE == redisTemplate.opsForValue().setIfAbsent(
            this.generateUserRequestHistoryCacheKey(couponEventId, userId),
            "1",
            Duration.ofSeconds(EXPIRE_SECONDS)
        )
    }

    fun getRequestSequentialNumber(couponEventId: Long): Long {
        val key = this.generateRequestCountHistoryCacheKey(couponEventId)
        val requestSequentialNumber: Long = redisTemplate.opsForValue().increment(key)?: 1L
        if (requestSequentialNumber == 1L) { // 만약 키가 처음 생성되었다면
            redisTemplate.expire(key, Duration.ofSeconds(EXPIRE_SECONDS)) // TTL 설정
        }
        return requestSequentialNumber
    }

    private fun generateUserRequestHistoryCacheKey(couponEventId: Long, userId: Long): String {
        return USER_REQUEST_HISTORY_KEY_PREFIX + couponEventId + ":" + userId
    }

    private fun generateRequestCountHistoryCacheKey(couponEventId: Long): String {
        return REQUEST_COUNT_HISTORY_KEY_PREFIX + couponEventId
    }
}