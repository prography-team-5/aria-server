package com.aria.server.art.infrastructure.rest.dto

import com.aria.server.art.domain.art.Size

data class GetRandomArtResponse(
    val artId: Long,
    val memberId: Long,
    val mainImageUrl: String,
    val title: String,
    val year: Int,
    val styles: List<String>,
    val size: Size,
    val description: String
)
