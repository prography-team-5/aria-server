package com.aria.server.art.infrastructure.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import io.swagger.v3.oas.annotations.media.Schema

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Response from server")
@JsonPropertyOrder(*["msg", "data"])
data class Response (
    // TODO code 로 바꿔야 함
    val msg: String,
    val data: Any?
)