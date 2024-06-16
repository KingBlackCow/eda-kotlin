package com.example.api.worker

import com.example.api.adapter.originalpost.OperationType
import com.example.api.common.Log
import com.example.api.common.Topic
import com.example.api.domain.postlist.PostSearchAdapter
import com.example.api.producer.InspectedPostMessage
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ContentIndexingWorker(
    private val objectMapper: ObjectMapper,
    private val postSearchAdapter: PostSearchAdapter
) {
    companion object: Log
    @KafkaListener(topics = [Topic.INSPECTED_POST], groupId = "indexing-post-consumer-group", concurrency = "3")
    @Throws(
        JsonProcessingException::class
    )
    fun listen(message: ConsumerRecord<String?, String?>) {
        val inspectedPostMessage: InspectedPostMessage =
            objectMapper.readValue(message.value(), InspectedPostMessage::class.java)
        log.info("indexing-post-consumer: {}", inspectedPostMessage.id)
        if (inspectedPostMessage.operationType === OperationType.CREATE) {
            this.handleCreate(inspectedPostMessage)
        } else if (inspectedPostMessage.operationType === OperationType.UPDATE) {
            this.handleUpdate(inspectedPostMessage)
        } else if (inspectedPostMessage.operationType === OperationType.DELETE) {
            this.handleDelete(inspectedPostMessage)
        }
    }

    private fun handleCreate(inspectedPostMessage: InspectedPostMessage) {
        postSearchAdapter.indexPost(inspectedPostMessage.toModel())
    }

    private fun handleUpdate(inspectedPostMessage: InspectedPostMessage) {
        postSearchAdapter.indexPost(inspectedPostMessage.toModel())
    }

    private fun handleDelete(inspectedPostMessage: InspectedPostMessage) {
        postSearchAdapter.deletePost(inspectedPostMessage.id)
    }
}