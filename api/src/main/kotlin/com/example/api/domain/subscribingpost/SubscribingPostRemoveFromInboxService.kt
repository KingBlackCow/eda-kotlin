package com.example.api.domain.subscribingpost

import org.springframework.stereotype.Service

@Service
class SubscribingPostRemoveFromInboxService(
    private val subscribingPostAdapter: SubscribingPostAdapter
) {
    fun deleteSubscribingInboxPost(postId: Long) {
        subscribingPostAdapter.removePostFromFollowerInboxes(postId)
    }
}