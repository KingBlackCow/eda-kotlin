package com.example.api.domain.post

import java.time.LocalDateTime

class Post(
    val id: Long? = null,
    var title: String? = null,
    var content: String? = null,
    val userId: Long? = null,
    var categoryId: Long? = null,
    val createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
    var deletedAt: LocalDateTime? = null
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