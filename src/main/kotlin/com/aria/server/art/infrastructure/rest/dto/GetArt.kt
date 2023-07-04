package com.aria.server.art.infrastructure.rest.dto

import com.aria.server.art.domain.art.Art

data class GetArtResponseDto(
    val artistNickname: String,
    val imagesUrl: List<String>,
    val title: String,
    val year: Int,
    val style: String,
    val artTags: List<String>,
    val size: SizeDto,
    val description: String,
){
    companion object {
        fun from(art: Art): GetArtResponseDto =
            GetArtResponseDto(
                artistNickname = art.member.nickname,
                imagesUrl = listOf(art.mainImage.url) + art.images.map { it.url },
                title = art.title,
                year = art.year,
                style = art.style,
                artTags = art.artTags.map { it.name },
                size = SizeDto(art.size.width, art.size.height),
                description = art.description
            )
    }
}