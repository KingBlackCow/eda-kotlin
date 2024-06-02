package com.example.api.domain.post

import com.example.api.adapter.originalpost.OperationType
import com.example.api.adapter.originalpost.OriginalPostMessage
import com.example.api.adapter.originalpost.Payload
import com.example.api.common.Topic.ORIGINAL_POST
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class OriginalPostMessageProduceAdapter(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {
    fun sendCreateMessage(post: Post) {
        val message: OriginalPostMessage = this.convertToMessage(post.id, post, OperationType.CREATE)
        this.sendMessage(message)
    }

    fun sendUpdateMessage(post: Post) {
        val message: OriginalPostMessage = this.convertToMessage(post.id, post, OperationType.UPDATE)
        this.sendMessage(message)
    }

    fun sendDeleteMessage(postId: Long) {
        val message: OriginalPostMessage = this.convertToMessage(postId, null, OperationType.DELETE)
        this.sendMessage(message)
    }

    private fun convertToMessage(id: Long?, post: Post?, operationType: OperationType): OriginalPostMessage {
        return OriginalPostMessage(
            id,
            if (post == null) null else Payload(post.id, post.title, post.content, post.userId, post.categoryId, post.createdAt, post.updatedAt, post.deletedAt),
            operationType
        )
    }

    private fun sendMessage(message: OriginalPostMessage) {
        try {
            kafkaTemplate.send(ORIGINAL_POST, message.id.toString(), objectMapper.writeValueAsString(message))
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }
    }
}