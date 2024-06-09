package com.example.api.adapter.chatgpt

import com.example.api.domain.post.Post
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class PostAutoInspectAdapter(
    private val chatGptClient: ChatGptClient,
    private val objectMapper: ObjectMapper
) {
    fun inspect(post: Post, categoryName: String): AutoInspectionResult {
        val contentString: String = buildContentString(post, categoryName)
        val chatPolicy = ChatPolicy(
            AutoInspectionPolicy.INSPECTION_INSTRUCTION,
            AutoInspectionPolicy.EXAMPLE_CONTENT,
            AutoInspectionPolicy.EXAMPLE_INSPECTION_RESULT
        )
        try {
            val resultString = chatGptClient.getResultForContentWithPolicy(contentString, chatPolicy)
            return objectMapper.readValue(resultString, AutoInspectionResult::class.java)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun buildContentString(post: Post, categoryName: String): String {
        return java.lang.String.format(
            "[%s] %s - %s",
            categoryName,
            post.title,
            post.content
        )
    }

    internal object AutoInspectionPolicy {
        const val INSPECTION_INSTRUCTION =
            "The task you need to accomplish is to return two items ('status' and 'tags') in JSON format. " +
                    "The information I will provide will be in the format '[Post category] Post content.' " +
                    "Then, if the content of the post aligns well with the meaning or theme of the post category, " +
                    "fill the 'status' field with the string 'GOOD.' " +
                    "If the meaning or theme appears unrelated, " +
                    "fill the 'status' field with the string 'BAD.' " +
                    "Additionally, extract and compile a list of up to 5 keywords " +
                    "that seem most important in the post content and populate the 'tags' field with them."
        const val EXAMPLE_CONTENT = "[Health] Reps and Muscle Size - To increase muscle size, " +
                "it is considered most ideal to exercise with the maximum weight " +
                "that allows 8 to 12 repetitions per set."
        const val EXAMPLE_INSPECTION_RESULT =
            "{\"status\":\"GOOD\",\"tags\":[\"muscle\", \"weight\", \"repetitions\"]}"
    }
}

data class AutoInspectionResult(
    @JsonProperty("status") val status: String, // "GOOD" or "BAD"
    @JsonProperty("tags") val tags: List<String>
)
