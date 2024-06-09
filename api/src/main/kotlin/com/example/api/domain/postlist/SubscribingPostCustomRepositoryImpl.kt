package com.example.api.domain.postlist

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

interface SubscribingPostCustomRepository {
    fun findByFollowerUserIdWithPagination(
        followerUserId: Long,
        pageNumber: Int,
        pageSize: Int
    ): List<SubscribingPostDocument>

    fun deleteAllByPostId(postId: Long)
}

@Repository
class SubscribingPostCustomRepositoryImpl(val mongoTemplate: MongoTemplate) : SubscribingPostCustomRepository {

    override fun findByFollowerUserIdWithPagination(
        followerUserId: Long,
        pageNumber: Int,
        pageSize: Int
    ): List<SubscribingPostDocument> {
        val query = Query()
            .addCriteria(Criteria.where("followerUserId").`is`(followerUserId))
            .with(
                PageRequest.of(
                    pageNumber,
                    pageSize,
                    Sort.by(Sort.Direction.DESC, "postCreatedAt")
                )
            )
        return mongoTemplate.find(query, SubscribingPostDocument::class.java, "subscribingInboxPosts")
    }

    override fun deleteAllByPostId(postId: Long) {
        val query = Query()
        query.addCriteria(Criteria.where("postId").`is`(postId))
        mongoTemplate.remove(query, SubscribingPostDocument::class.java)
    }
}