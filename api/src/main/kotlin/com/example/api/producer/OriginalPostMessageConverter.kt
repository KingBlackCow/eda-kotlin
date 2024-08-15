package com.example.api.producer

import com.example.api.adapter.originalpost.OriginalPostMessage
import com.example.api.domain.post.Post

class OriginalPostMessageConverter {
    companion object {
        fun toModel(originalPostMessage: OriginalPostMessage): Post {
            return Post(
                originalPostMessage.payload?.id,
                originalPostMessage.payload?.title,
                originalPostMessage.payload?.content,
                originalPostMessage.payload?.userId!!,
                originalPostMessage.payload.categoryId,
                originalPostMessage.payload.createdAt,
                originalPostMessage.payload.updatedAt,
                originalPostMessage.payload.deletedAt
            )
        }
    }
}