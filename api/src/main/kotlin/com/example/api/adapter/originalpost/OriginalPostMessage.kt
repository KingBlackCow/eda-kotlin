package com.example.api.adapter.originalpost

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class OriginalPostMessage (
    @JsonProperty("id") val id: Long? = null,
    @JsonProperty("payload") val payload: Payload? = null,
    @JsonProperty("operationType") val operationType: OperationType
)

data class Payload (
    @JsonProperty("id") val id: Long? = null,
    @JsonProperty("title") val title: String? = null,
    @JsonProperty("content") val content: String? = null,
    @JsonProperty("userId") val userId: Long? = null,
    @JsonProperty("categoryId") val categoryId: Long? = null,
    @JsonProperty("createdAt") val createdAt: LocalDateTime? = null,
    @JsonProperty("updatedAt") val updatedAt: LocalDateTime? = null,
    @JsonProperty("deletedAt") val deletedAt: LocalDateTime? = null
)

enum class OperationType {
    CREATE,
    UPDATE,
    DELETE
}
