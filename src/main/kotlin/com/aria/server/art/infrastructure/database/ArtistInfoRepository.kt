package com.aria.server.art.infrastructure.database

import com.aria.server.art.domain.artistinfo.ArtistInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ArtistInfoRepository: JpaRepository<ArtistInfo, Long> {
    fun findByMemberId(id: Long): ArtistInfo?
    fun existsByMemberId(id: Long): Boolean
    @Query(value = "SELECT * FROM artist_info ORDER BY RAND() LIMIT 1", nativeQuery = true)
    fun findRandomArtistInfo(): ArtistInfo?
}