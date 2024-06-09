package com.example.api.adapter.metadata

import org.springframework.stereotype.Component

@Component
class MetadataAdapter(
    private val metadataClient: MetadataClient
) {
    fun getCategoryNameByCategoryId(categoryId: Long): String? {
        val categoryResponse = metadataClient.getCategoryById(categoryId!!)
        return categoryResponse.name
    }

    fun getUserNameByUserId(userId: Long?): String? {
        val userResponse = metadataClient.getUserById(userId!!) ?: return null
        return userResponse.name
    }

    fun listFollowerIdsByUserId(userId: Long): List<Long> {
        return metadataClient.getFollowerIdsByUserId(userId)
    }
}