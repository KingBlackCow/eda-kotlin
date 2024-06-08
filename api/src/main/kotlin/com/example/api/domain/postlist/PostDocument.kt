package com.example.api.domain.postlist

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.DateFormat
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime


@Document(indexName = "post-1")
data class PostDocument(
    @Id
    val id: Long,
    val title: String? = null,
    val content: String? = null,
    val categoryName: String? = null,
    val tags: List<String>? = null,
    @Field(type = FieldType.Date, format = [DateFormat.date_hour_minute_second_millis])
    val indexedAt: LocalDateTime? = null
) {

}
