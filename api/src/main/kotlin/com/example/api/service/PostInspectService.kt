package com.example.api.service

import com.example.api.adapter.chatgpt.AutoInspectionResult
import com.example.api.adapter.chatgpt.PostAutoInspectAdapter
import com.example.api.adapter.metadata.MetadataAdapter
import com.example.api.domain.inspectedPost.InspectedPost
import com.example.api.domain.post.Post
import org.springframework.stereotype.Service
import java.util.*

@Service
class PostInspectService(
    private val metadataAdapter: MetadataAdapter,
    private val postAutoInspectAdapter: PostAutoInspectAdapter
) {
    fun inspectAndGetIfValid(post: Post): InspectedPost? {
        val categoryName: String = metadataAdapter.getCategoryNameByCategoryId(post.categoryId)
        val inspectionResult: AutoInspectionResult = postAutoInspectAdapter.inspect(post, categoryName)
        if (!inspectionResult.status.equals("GOOD")) return null
        return InspectedPost.generate(
            post,
            categoryName,
            inspectionResult.tags
        )
    }
}