package com.example.api.controller

import com.example.api.domain.inspectedPost.InspectedPost
import com.example.api.domain.post.Post
import com.example.api.domain.post.PostInspectService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/internal")
class InternalController(
    private val postInspectService: PostInspectService
) {
    @GetMapping
    fun inspectionTest(
        @RequestParam("title") title: String,
        @RequestParam("content") content: String,
        @RequestParam("categoryId") categoryId: Long
    ): InspectedPost? {
        return postInspectService.inspectAndGetIfValid(
            Post.generate(0L, title, content, categoryId)
        )
    }
}