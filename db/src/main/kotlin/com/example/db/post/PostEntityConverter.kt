package com.example.db.post

import com.example.domain.post.Post

object PostEntityConverter {
    fun toEntity(post: Post): PostEntity {
        return PostEntity(
            id = post.id,
            title = post.title,
            content = post.content,
            userId = post.userId,
            categoryId = post.categoryId,
            createdAt = post.createdAt,
            updatedAt = post.updatedAt,
            deletedAt = post.deletedAt
        )
    }

    fun toModel(postEntity: PostEntity): Post {
        return Post(
            id = postEntity.id,
            title = postEntity.title,
            content = postEntity.content,
            userId = postEntity.userId,
            categoryId = postEntity.categoryId,
            createdAt = postEntity.createdAt,
            updatedAt = postEntity.updatedAt,
            deletedAt = postEntity.deletedAt,
        )
    }
}
