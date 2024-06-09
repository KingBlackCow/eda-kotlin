package com.example.api.worker

import com.example.api.adapter.originalpost.OperationType
import com.example.api.common.Log
import com.example.api.common.Topic
import com.example.api.domain.subscribingpost.SubscribingPostAddToInboxService
import com.example.api.domain.subscribingpost.SubscribingPostRemoveFromInboxService
import com.example.api.producer.InspectedPostMessage
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ContentSubscribingWorker(
    private val objectMapper: ObjectMapper,
    private val subscribingPostAddToInboxService: SubscribingPostAddToInboxService,
    private val subscribingPostRemoveFromInboxService: SubscribingPostRemoveFromInboxService
) {
    companion object: Log

    @KafkaListener(topics = [Topic.INSPECTED_POST], groupId = "subscribing-post-consumer-group", concurrency = "3")
    @Throws(JsonProcessingException::class)
    fun listen(message: ConsumerRecord<String, String>) {
        val inspectedPostMessage: InspectedPostMessage =
            objectMapper.readValue(message.value(), InspectedPostMessage::class.java)
        log.info("subscribing-post-consumer: ${inspectedPostMessage.id}")
        if (inspectedPostMessage.operationType === OperationType.CREATE) {
            this.handleCreate(inspectedPostMessage)
        } else if (inspectedPostMessage.operationType === OperationType.UPDATE) {
            // DO NOTHING
        } else if (inspectedPostMessage.operationType === OperationType.DELETE) {
            this.handleDelete(inspectedPostMessage)
        }
    }

    private fun handleCreate(inspectedPostMessage: InspectedPostMessage) {
        subscribingPostAddToInboxService.saveSubscribingInboxPost(inspectedPostMessage.payload?.post!!)
    }

    private fun handleDelete(inspectedPostMessage: InspectedPostMessage) {
        subscribingPostRemoveFromInboxService.deleteSubscribingInboxPost(inspectedPostMessage.id)
    }
}