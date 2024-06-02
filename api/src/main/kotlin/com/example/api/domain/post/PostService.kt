package com.example.api.domain.post

import com.example.api.db.post.PostAdapter
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postAdapter: PostAdapter,
    private val postResolvingHelpService: PostResolvingHelpService,
    private val originalPostMessageProduceAdapter: OriginalPostMessageProduceAdapter
) {
    @Transactional
    fun create(request: CreateRequest): Post {
        val post = Post.generate(
            request.userId,
            request.title,
            request.content,
            request.categoryId
        )
        val savedPost = postAdapter.save(post)
        originalPostMessageProduceAdapter.sendCreateMessage(savedPost)
        return savedPost
    }

    fun getById(id: Long): ResolvedPost? {
        return postResolvingHelpService.resolvePostById(id)
    }

    @Transactional
    fun update(updateRequest: UpdateRequest): Post? {
        val post: Post = postAdapter.findById(updateRequest.postId) ?: return null
        post.update(
            updateRequest.title,
            updateRequest.content,
            updateRequest.categoryId
        )
        val savedPost: Post = postAdapter.save(post)
        originalPostMessageProduceAdapter.sendUpdateMessage(savedPost)
        return savedPost
    }

    @Transactional
    fun delete(request: DeleteRequest): Post? {
        val post = postAdapter.findById(request.postId) ?: return null
        post.delete()
        val savedPost = postAdapter.save(post)
        originalPostMessageProduceAdapter.sendDeleteMessage(savedPost.id!!)
        return savedPost
    }

}

data class CreateRequest (
    val userId: Long,
    val title: String,
    val content: String,
    val categoryId: Long
)

data class UpdateRequest(
    val postId: Long,
    val title: String,
    val content: String,
    val categoryId: Long
)


data class DeleteRequest(
    val postId: Long
)

