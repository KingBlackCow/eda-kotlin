package com.example.api.adapter.originalpost

import java.time.LocalDateTime

data class OriginalPostMessage (
    val id: Long? = null,
    val payload: Payload? = null,
    val operationType: OperationType
)

data class Payload (
    val id: Long? = null,
    val title: String? = null,
    val content: String? = null,
    val userId: Long? = null,
    val categoryId: Long? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null
)

enum class OperationType {
    CREATE,
    UPDATE,
    DELETE
}
