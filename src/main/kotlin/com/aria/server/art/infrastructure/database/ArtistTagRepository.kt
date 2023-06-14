package com.aria.server.art.infrastructure.database

import com.aria.server.art.domain.artisttag.ArtistTag
import org.springframework.data.jpa.repository.JpaRepository

interface ArtistTagRepository: JpaRepository<ArtistTag, Long> {
    fun existsByName(name: String): Boolean
    fun findArtistTagByMemberId(id: Long): MutableList<ArtistTag>
}