package com.example.api.domain.post

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

class Post(
    @JsonProperty("id") val id: Long? = null,
    @JsonProperty("title") var title: String? = null,
    @JsonProperty("content") var content: String? = null,
    @JsonProperty("userId") val userId: Long,
    @JsonProperty("categoryId") var categoryId: Long? = null,
    @JsonProperty("createdAt") val createdAt: LocalDateTime? = null,
    @JsonProperty("updatedAt") var updatedAt: LocalDateTime? = null,
    @JsonProperty("deletedAt") var deletedAt: LocalDateTime? = null
) {


    fun update(title: String?, content: String?, categoryId: Long?): Post {
        this.title = title
        this.content = content
        this.categoryId = categoryId
        this.updatedAt = LocalDateTime.now()
        return this
    }

    fun delete(): Post {
        val now = LocalDateTime.now()
        this.updatedAt = now
        this.deletedAt = now
        return this
    }

    fun undelete(): Post {
        this.deletedAt = null
        return this
    }

    companion object {
        fun generate(
            userId: Long,
            title: String,
            content: String,
            categoryId: Long
        ): Post {
            val now = LocalDateTime.now()
            return Post(null, title, content, userId, categoryId, now, now, null)
        }
    }
}