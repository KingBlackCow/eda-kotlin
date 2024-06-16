package com.example.api.domain.coupon

import com.example.api.adapter.couponissuerequest.CouponIssueRequestMessage
import com.example.api.common.Topic.COUPON_ISSUE_REQUEST
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class CouponIssueRequestAdapter(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {
    fun sendMessage(userId: Long, couponEventId: Long) {
        val message: CouponIssueRequestMessage = CouponIssueRequestMessage.from(userId, couponEventId)
        try {
            kafkaTemplate.send(
                COUPON_ISSUE_REQUEST,
                message.userId.toString(),
                objectMapper.writeValueAsString(message)
            )
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }
    }
}