package com.example.api.domain.post

import com.example.api.adapter.chatgpt.AutoInspectionResult
import com.example.api.adapter.chatgpt.PostAutoInspectAdapter
import com.example.api.adapter.metadata.MetadataAdapter
import com.example.api.domain.inspectedPost.InspectedPost
import org.springframework.stereotype.Service

@Service
class PostInspectService(
    private val metadataAdapter: MetadataAdapter,
    private val postAutoInspectAdapter: PostAutoInspectAdapter
) {
    fun inspectAndGetIfValid(post: Post): InspectedPost? {
        val categoryName: String = metadataAdapter.getCategoryNameByCategoryId(post.categoryId!!).toString()
        val inspectionResult: AutoInspectionResult = postAutoInspectAdapter.inspect(post, categoryName)
        if (!inspectionResult.status.equals("GOOD")) return null
        return InspectedPost.generate(
            post,
            categoryName,
            inspectionResult.tags
        )
    }
}