package com.example.api.domain.post

import com.example.api.adapter.metadata.MetadataAdapter
import com.example.api.db.post.PostAdapter
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors

@Service
class PostResolvingHelpService(
    private val postAdapter: PostAdapter,
    private val metadataAdapter: MetadataAdapter,
    private val resolvedPostCacheAdapter: ResolvedPostCacheAdapter
) {
    fun resolvePostById(postId: Long): ResolvedPost? {
        var resolvedPost = resolvedPostCacheAdapter.get(postId)
        if (resolvedPost != null) {
            return resolvedPost
        }
        val post = postAdapter.findById(postId)
        if (post != null) {
            resolvedPost = this.resolvePost(post)
        }
        return resolvedPost
    }

    fun resolvePostsByIds(postIds: List<Long>?): List<ResolvedPost> {
        if (postIds == null || postIds.isEmpty()) return listOf()

        val resolvedPostCaches: MutableList<ResolvedPost> = ArrayList<ResolvedPost>(resolvedPostCacheAdapter.multiGet(postIds))

        val missingPostIds: List<Long> = postIds.stream()
            .filter { postId -> resolvedPostCaches.stream().noneMatch { resolvedPost -> resolvedPost.id.equals(postId) }}
            .toList()

        val missingPosts = postAdapter.listByIds(missingPostIds)
        val missingResolvedPosts: MutableList<ResolvedPost> = missingPosts.stream()
            .map(this::resolvePost)
            .filter(Objects::nonNull)
            .toList() as MutableList<ResolvedPost>

        resolvedPostCaches.addAll(missingResolvedPosts)

        // postIds를 기준으로 resolvedPostCaches를 매핑하는 Map 생성
        val resolvedPostMap: MutableMap<Long, ResolvedPost> = resolvedPostCaches.stream()
            .collect(Collectors.toMap(ResolvedPost::id, Function.identity()))

        return postIds.stream().map(resolvedPostMap::get)
            .filter(Objects::nonNull)
            .toList() as List<ResolvedPost>
    }

    private fun resolvePost(post: Post?): ResolvedPost? {
        if (post == null) return null
        var resolvedPost: ResolvedPost? = null
        val userName = metadataAdapter.getUserNameByUserId(post.userId)
        val categoryName = metadataAdapter.getCategoryNameByCategoryId(post.categoryId)
        if (userName != null && categoryName != null) {
            resolvedPost = ResolvedPost.generate(post, userName, categoryName)
            resolvedPostCacheAdapter.set(resolvedPost)
        }
        return resolvedPost
    }
}
