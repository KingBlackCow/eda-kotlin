package com.example.api.adapter.metadata

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class MetadataClient(
    private val metadataWebClient: WebClient
) {

    fun getCategoryById(categoryId: Long): CategoryResponse {
        return metadataWebClient
            .get()
            .uri("/categories/$categoryId")
            .retrieve()
            .bodyToMono(CategoryResponse::class.java)
            .block()!!
    }

    fun getUserById(userId: Long): UserResponse {
        return metadataWebClient
            .get()
            .uri("/users/$userId")
            .retrieve()
            .bodyToMono(UserResponse::class.java)
            .block()!!
    }

    fun getFollowerIdsByUserId(userId: Long): List<Long> {
        return metadataWebClient
            .get()
            .uri("/followers?followingId=$userId")
            .retrieve()
            .bodyToFlux(Long::class.java)
            .collectList()
            .block()!!
    }

    data class CategoryResponse(
        val id: Long,
        val name: String
    )

    data class UserResponse(
        val id: Long,
        val email: String,
        val name: String
    )
}