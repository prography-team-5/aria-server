package com.aria.server.art.infrastructure.rest.dto

import com.aria.server.art.domain.art.ArtTag
import com.aria.server.art.domain.art.Size

data class EditArtImagesRequest(
    val artId: Long,
    val imageIds: List<Long>,
    val originImageIds: List<Long>,
)

data class EditArtTitleRequest(
    val artId: Long,
    val title: String
)

data class EditArtYearRequest(
    val artId: Long,
    val year: Int
)

data class EditArtSizeRequest(
    val artId: Long,
    val size: Size
)

data class EditArtStyleRequest(
    val artId: Long,
    val style: String
)

data class EditArtTagsRequest(
    val artId: Long,
    val tags: List<ArtTag>
)

data class EditArtDescriptionRequest(
    val artId: Long,
    val description: String
)