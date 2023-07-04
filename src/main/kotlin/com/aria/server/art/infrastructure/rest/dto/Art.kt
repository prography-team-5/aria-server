package com.aria.server.art.infrastructure.rest.dto

import com.aria.server.art.domain.art.Size
import com.aria.server.art.domain.art.Style
import java.time.LocalDateTime

data class SimpleArtDto(
    val title: String,
    val mainImageUrl: String,
    val year: Int,
    val size: Size,
    val styles: MutableList<Style>,
    val createdAt: LocalDateTime
)
