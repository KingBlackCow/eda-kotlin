package com.example.api.domain.postlist

import com.example.api.domain.post.Post
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "subscribingInboxPosts")
class SubscribingPostDocument(
    @Id
    var id: String,
    val postId: Long,
    val followerUserId: Long, // follower(구독자) user id
    val postCreatedAt: LocalDateTime? = null, // 컨텐츠의 생성시점
    val addedAt: LocalDateTime? = null, // follower 유저의 구독 목록에 반영된 시점
    val read: Boolean =  false, // 해당 구독 컨텐츠 조회 여부
) {
    companion object {
        fun generate(
            post: Post,
            followerUserId: Long
        ): SubscribingPostDocument {
            return SubscribingPostDocument(
                generateDocumentId(post.id!!, followerUserId),
                post.id,
                followerUserId!!,
                post.createdAt,
                LocalDateTime.now(),
                false
            )
        }
        private fun generateDocumentId(postId: Long, followerUserId: Long): String {
            return postId.toString() + "_" + followerUserId
        }
    }
}