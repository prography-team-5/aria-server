package com.aria.server.art.infrastructure.rest.dto

import com.aria.server.art.domain.art.Art
import com.aria.server.art.domain.art.Size

data class GetRandomArtResponse(
    val arts: List<GetRandomArtDto>
)

data class GetRandomArtDto(
    val artId: Long,
    val memberId: Long,
    val mainImageUrl: String,
    val style: String,
    val title: String,
    val year: Int,
    val artTags: List<String>,
    val size: Size,
    val description: String
) {

    companion object {
        fun from(art: Art): GetRandomArtDto =
            GetRandomArtDto(
                artId = art.id,
                memberId = art.member.id,
                mainImageUrl = art.mainImage.url,
                style = art.style,
                title = art.title,
                year = art.year,
                artTags = art.artTags.map { it.name },
                size = art.size,
                description = art.description
            )
    }
}
