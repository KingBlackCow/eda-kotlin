package com.example.api.adapter.chatgpt

import com.fasterxml.jackson.annotation.JsonProperty

data class ChatCompletionResponse(
    val id: String? = null,
    val `object`: String? = null,
    val created: Long = 0,
    val model: String? = null,
    val choices: Array<ChatChoice>,
    val usage: Usage? = null,
    @JsonProperty("system_fingerprint")
    val systemFingerprint: String? = null,
)

data class ChatChoice (
    val index: Int = 0,
    val message: Message,
    val logprobs: Any? = null,
    @JsonProperty("finish_reason")
    val finishReason: String? = null
)

data class Message(
    val role: String,
    val content: String
)

data class Usage(
    @JsonProperty("prompt_tokens")
    val promptTokens: Int = 0,
    @JsonProperty("completion_tokens")
    val completionTokens: Int = 0,
    @JsonProperty("total_tokens")
    val totalTokens: Int = 0
)