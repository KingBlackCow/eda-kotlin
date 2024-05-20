package com.example.db.post

import com.example.domain.post.Post
import org.springframework.stereotype.Component

@Component
class PostAdapter(
    private val postJpaRepository: PostJpaRepository
) {

    fun save(post: Post): Post {
        val postEntity = postJpaRepository.save(PostEntityConverter.toEntity(post))
        return PostEntityConverter.toModel(postEntity)
    }

    fun findById(id: Long): Post? {
        val postEntity = postJpaRepository.findById(id).orElse(null) ?: return null
        return PostEntityConverter.toModel(postEntity)
    }

    fun listByIds(ids: List<Long>): List<Post> {
        val postEntities = postJpaRepository.findAllById(ids)
        return postEntities.map { PostEntityConverter.toModel(it) }
    }
}
