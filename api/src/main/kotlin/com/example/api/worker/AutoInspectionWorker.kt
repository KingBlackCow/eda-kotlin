package com.example.api.worker

import com.example.api.adapter.originalpost.OperationType
import com.example.api.adapter.originalpost.OriginalPostMessage
import com.example.api.common.Log
import com.example.api.common.Topic
import com.example.api.domain.inspectedPost.InspectedPost
import com.example.api.domain.post.PostInspectService
import com.example.api.producer.InspectedPostMessageProduceAdapter
import com.example.api.producer.OriginalPostMessageConverter
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class AutoInspectionWorker(
    private val postInspectService: PostInspectService,
    private val inspectedPostMessageProduceAdapter: InspectedPostMessageProduceAdapter,
    private val objectMapper: ObjectMapper
) {
    companion object: Log

    @KafkaListener(topics = [Topic.ORIGINAL_POST], groupId = "auto-inspection-consumer-group", concurrency = "3")
    @Throws(
        JsonProcessingException::class
    )
    fun listen(message: ConsumerRecord<String, String>) {
        val originalPostMessage: OriginalPostMessage =
            objectMapper.readValue(message.value(), OriginalPostMessage::class.java)
        log.info("auto-inspection-consumer: ${originalPostMessage.id}")
        if (originalPostMessage.operationType === OperationType.CREATE) {
            this.handleCreate(originalPostMessage)
        } else if (originalPostMessage.operationType === OperationType.UPDATE) {
            this.handleUpdate(originalPostMessage)
        } else if (originalPostMessage.operationType === OperationType.DELETE) {
            this.handleDelete(originalPostMessage)
        }
    }

    private fun handleCreate(originalPostMessage: OriginalPostMessage) {
        val inspectedPost: InspectedPost? = postInspectService.inspectAndGetIfValid(
            OriginalPostMessageConverter.toModel(originalPostMessage)
        )
        if (inspectedPost == null) {
            return
        }
        inspectedPostMessageProduceAdapter.sendCreateMessage(inspectedPost)
    }

    private fun handleUpdate(originalPostMessage: OriginalPostMessage) {
        val inspectedPost: InspectedPost? = postInspectService.inspectAndGetIfValid(
            OriginalPostMessageConverter.toModel(originalPostMessage)
        )
        if (inspectedPost == null) {
            inspectedPostMessageProduceAdapter.sendDeleteMessage(originalPostMessage.id!!)
        } else {
            inspectedPostMessageProduceAdapter.sendUpdateMessage(inspectedPost)
        }
    }

    private fun handleDelete(originalPostMessage: OriginalPostMessage) {
        // DELETE 메시지는 검수가 필요 없으므로 바로 삭제
        inspectedPostMessageProduceAdapter.sendDeleteMessage(originalPostMessage.id!!)
    }
}