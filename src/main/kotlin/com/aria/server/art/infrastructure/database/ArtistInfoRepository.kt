package com.aria.server.art.infrastructure.database

import com.aria.server.art.domain.artistinfo.ArtistInfo
import org.springframework.data.jpa.repository.JpaRepository

interface ArtistInfoRepository: JpaRepository<ArtistInfo, Long> {
    fun findByMemberId(id: Long): ArtistInfo?
    fun existsByMemberId(id: Long): Boolean
}