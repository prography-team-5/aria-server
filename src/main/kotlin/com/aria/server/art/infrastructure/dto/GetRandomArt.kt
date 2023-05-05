package com.aria.server.art.infrastructure.dto

import com.aria.server.art.domain.Size

data class GetRandomArtResponse(
    val artId: Long,
    val memberId: Long,
    val imageUrl: String,
    val title: String,
    val year: Int,
    val styles: List<String>,
    val size: Size,
    val description: String
)
