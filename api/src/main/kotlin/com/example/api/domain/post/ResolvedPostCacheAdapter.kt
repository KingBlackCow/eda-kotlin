package com.example.api.domain.post

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*

private const val KEY_PREFIX = "resolved_post:v1:"
private const val EXPIRE_SECONDS = 60 * 60 * 24 * 7L

@Component
class ResolvedPostCacheAdapter(
    private val objectMapper: ObjectMapper,
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun set(resolvedPost: ResolvedPost) {
        val jsonString: String
        try {
            jsonString = objectMapper.writeValueAsString(resolvedPost)
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }
        redisTemplate.opsForValue()[generateCacheKey(resolvedPost.id), jsonString] =
            Duration.ofSeconds(EXPIRE_SECONDS)
    }

    fun get(postId: Long): ResolvedPost? {
        val jsonString = redisTemplate.opsForValue()[generateCacheKey(postId)] ?: return null
        try {
            return objectMapper.readValue(jsonString, ResolvedPost::class.java)
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }
    }

    fun multiGet(postIds: List<Long>): List<ResolvedPost> {
        val jsonStrings = redisTemplate.opsForValue()
            .multiGet(postIds.stream().map { postId -> this.generateCacheKey(postId) }.toList())
        if (jsonStrings == null) return listOf()
        return jsonStrings.stream().filter { obj -> Objects.nonNull(obj) }.map { jsonString: String? ->
            try {
                return@map objectMapper.readValue(jsonString, ResolvedPost::class.java)
            } catch (e: JsonProcessingException) {
                throw RuntimeException(e)
            }
        }.toList()
    }

    fun delete(postId: Long) {
        redisTemplate.delete(this.generateCacheKey(postId))
    }

    private fun generateCacheKey(postId: Long): String {
        return KEY_PREFIX + postId
    }
}