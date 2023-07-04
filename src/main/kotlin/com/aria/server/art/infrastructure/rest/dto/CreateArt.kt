package com.aria.server.art.infrastructure.rest.dto

data class CreateArtRequest(
    val title: String,
    val year: Int,
    val style: String,
    val artTags: List<String>,
    val size: SizeDto,
    val description: String,
    val artImageIds: List<Long>,
    val totalArtImageIds: List<Long>
)

data class SizeDto(
    val width: Float,
    val height: Float
)
data class CreateArtResponse(
    val postId: Long
)
