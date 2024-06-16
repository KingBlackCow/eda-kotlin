package com.example.api.worker

import com.example.api.adapter.couponissuerequest.CouponIssueRequestMessage
import com.example.api.common.Topic
import com.example.api.domain.coupon.IssueCouponService
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class CouponIssueWorker(
    private val objectMapper: ObjectMapper,
    private val issueCouponService: IssueCouponService
) {
    @KafkaListener(topics = [Topic.COUPON_ISSUE_REQUEST], groupId = "coupon-issue-request", concurrency = "3")
    @Throws(JsonProcessingException::class)
    fun listen(message: ConsumerRecord<String, String>) {
        val couponIssueRequestMessage: CouponIssueRequestMessage = objectMapper.readValue(
            message.value(),
            CouponIssueRequestMessage::class.java
        )
        issueCouponService.save(
            couponIssueRequestMessage.couponEventId,
            couponIssueRequestMessage.userId
        )
    }
}