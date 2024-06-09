package com.example.api.adapter.chatgpt

import com.fasterxml.jackson.annotation.JsonProperty

data class ChatCompletionResponse(
    @JsonProperty("id") val id: String? = null,
    @JsonProperty("object") val `object`: String? = null,
    @JsonProperty("created") val created: Long = 0,
    @JsonProperty("model") val model: String? = null,
    @JsonProperty("choices") val choices: List<ChatChoice>,
    @JsonProperty("usage") val usage: Usage? = null,
    @JsonProperty("system_fingerprint") val systemFingerprint: String? = null,
)

data class ChatChoice (
    @JsonProperty("index") val index: Int = 0,
    @JsonProperty("message") val message: Message,
    @JsonProperty("logprobs") val logprobs: Any? = null,
    @JsonProperty("finish_reason") val finishReason: String? = null
)

data class Message(
    @JsonProperty("role") val role: String,
    @JsonProperty("content")val content: String
)

data class Usage(
    @JsonProperty("prompt_tokens") val promptTokens: Int = 0,
    @JsonProperty("completion_tokens") val completionTokens: Int = 0,
    @JsonProperty("total_tokens") val totalTokens: Int = 0
)