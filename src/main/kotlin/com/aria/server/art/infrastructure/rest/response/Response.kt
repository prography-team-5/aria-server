package com.aria.server.art.infrastructure.rest.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import io.swagger.v3.oas.annotations.media.Schema


// TODO CODE 필드추가
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(*["message", "data"])
data class Response (
    val message: String,
    val data: Any?
) {
    companion object {
        fun success(message: String): Response =
             Response(message, null)
        fun success(message: String, data: Any): Response =
             Response(message, data)
        fun failure(message: String): Response =
             Response(message, null)
    }
}