package com.aria.server.art.infrastructure.rest.dto

data class CreateArtRequest(
    val title: String,
    val year: Int,
    val styles: List<String>,
    val size: Size,
    val description: String,
    val artImageIds: List<Long>,
    val totalArtImageIds: List<Long>
)

data class Size(
    val width: Int,
    val height: Int
)
data class CreateArtResponse(
    val postId: Long
)
