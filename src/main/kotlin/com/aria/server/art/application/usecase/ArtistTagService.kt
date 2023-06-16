package com.aria.server.art.application.usecase

import com.aria.server.art.domain.artisttag.ArtistTag

interface ArtistTagService {
    fun createArtistTag(artistTag: ArtistTag)
    fun deleteArtistTag(id: Long)
    fun getArtistTags(artistId: Long): MutableList<ArtistTag>
}