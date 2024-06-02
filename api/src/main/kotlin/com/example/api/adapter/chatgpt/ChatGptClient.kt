package com.example.api.adapter.chatgpt

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.util.List
import java.util.Map

@Component
class ChatGptClient(
    @param:Qualifier("chatGptWebClient") private val chatGptWebClient: WebClient,
    @Value("\${OPENAI_API_KEY}") private val openaiApiKey: String,
    private val objectMapper: ObjectMapper
) {
    companion object {
        private const val TARGET_GPT_MODEL = "gpt-3.5-turbo"
    }

    fun getResultForContentWithPolicy(content: String?, chatPolicy: ChatPolicy): String {
        val jsonString = chatGptWebClient
            .post()
            .uri("/v1/chat/completions")
            .header("Authorization", "Bearer $openaiApiKey") // OpenAI API 키 추가
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                Map.of(
                    "model", TARGET_GPT_MODEL,
                    "messages", List.of(
                        Map.of("role", "system", "content", chatPolicy.instruction),
                        Map.of("role", "user", "content", chatPolicy.exampleContent),
                        Map.of("role", "assistant", "content", chatPolicy.exampleInspectionResult),
                        Map.of("role", "user", "content", content)
                    ),
                    "stream", false
                )
            )
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
        try {
            val response: ChatCompletionResponse =
                objectMapper.readValue(jsonString, ChatCompletionResponse::class.java)
            return response.choices.get(0).message.content
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
        //        return "{\"status\":\"GOOD\",\"tags\":[\"muscle\", \"weight\", \"repetitions\"]}";
    }
}

data class ChatPolicy(
    internal val instruction: String? = null,
    val exampleContent: String? = null,
    val exampleInspectionResult: String? = null
)