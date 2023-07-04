package com.aria.server.art.infrastructure.rest.dto

import com.aria.server.art.domain.art.Art
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
) {
    companion object {
        fun from(art: Art): SimpleArtDto =
            SimpleArtDto(
                art.title,
                art.mainImage.url,
                art.year,
                art.size,
                art.styles,
                art.createdAt
            )
    }
}
