package com.example.api.domain.postlist

import org.springframework.data.mongodb.repository.MongoRepository

interface SubscribingPostRepository: SubscribingPostCustomRepository, MongoRepository<SubscribingPostDocument, String> {
}