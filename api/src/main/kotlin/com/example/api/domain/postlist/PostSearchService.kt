package com.example.api.domain.postlist

import com.example.api.domain.post.PostResolvingHelpService
import com.example.api.domain.post.ResolvedPost
import org.springframework.stereotype.Service

private const val PAGE_SIZE = 5

@Service
class PostSearchService(
    private val postSearchAdapter: PostSearchAdapter,
    private val postResolvingHelpService: PostResolvingHelpService
) {
    fun getSearchResultByKeyword(keyword: String?, pageNumber: Int): List<ResolvedPost> {
        // ES로부터 검색을 해서 post id 목록을 가져옴
        val postIds: List<Long> = postSearchAdapter.searchPostIdsByKeyword(keyword, pageNumber, PAGE_SIZE)
        // 가져온 post id 목록을 이용해서 resolved post를 가져옴
        return postResolvingHelpService.resolvePostsByIds(postIds)
    }
}