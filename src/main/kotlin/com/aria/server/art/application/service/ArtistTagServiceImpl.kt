package com.aria.server.art.application.service

import com.aria.server.art.application.usecase.ArtistTagService
import com.aria.server.art.domain.exception.artisttag.DuplicatedArtistTagException
import com.aria.server.art.domain.artisttag.ArtistTag
import com.aria.server.art.infrastructure.database.ArtistTagRepository
import org.springframework.stereotype.Service

@Service
class ArtistTagServiceImpl(
    private val artistTagRepository: ArtistTagRepository
): ArtistTagService {

    override fun createArtistTag(artistTag: ArtistTag): ArtistTag {
        if (artistTagRepository.existsByName(artistTag.name)) {
            throw DuplicatedArtistTagException()
        } else {
            return artistTagRepository.save(artistTag)
        }
    }

    override fun deleteArtistTag(id: Long) {
        artistTagRepository.deleteById(id)
    }

    override fun getArtistTags(artistId: Long): MutableList<ArtistTag> =
        artistTagRepository.findArtistTagByMemberId(artistId)
}