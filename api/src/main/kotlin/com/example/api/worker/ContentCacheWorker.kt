package com.example.api.worker

import com.example.api.adapter.originalpost.OperationType
import com.example.api.adapter.originalpost.OriginalPostMessage
import com.example.api.common.Log
import com.example.api.common.Topic
import com.example.api.domain.post.PostResolvingHelpService
import com.example.api.producer.OriginalPostMessageConverter
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ContentCacheWorker(
    private val objectMapper: ObjectMapper,
    private val postResolvingHelpService: PostResolvingHelpService
) {
    companion object: Log
    @KafkaListener(topics = [Topic.ORIGINAL_POST], groupId = "cache-post-consumer-group", concurrency = "3")
    @Throws(
        JsonProcessingException::class
    )
    fun listen(message: ConsumerRecord<String?, String?>) {
        val originalPostMessage: OriginalPostMessage =
            objectMapper.readValue(message.value(), OriginalPostMessage::class.java)
        log.info("cache-post-consumer: {}", originalPostMessage.id)
        if (originalPostMessage.operationType === OperationType.CREATE) {
            this.handleCreate(originalPostMessage)
        } else if (originalPostMessage.operationType === OperationType.UPDATE) {
            this.handleUpdate(originalPostMessage)
        } else if (originalPostMessage.operationType === OperationType.DELETE) {
            this.handleDelete(originalPostMessage)
        }
    }

    private fun handleCreate(originalPostMessage: OriginalPostMessage) {
        postResolvingHelpService.resolvePostAndSave(OriginalPostMessageConverter.toModel(originalPostMessage))
    }

    private fun handleUpdate(originalPostMessage: OriginalPostMessage) {
        postResolvingHelpService.resolvePostAndSave(OriginalPostMessageConverter.toModel(originalPostMessage))
    }

    private fun handleDelete(originalPostMessage: OriginalPostMessage) {
        postResolvingHelpService.deleteResolvedPost(originalPostMessage.id!!)
    }
}