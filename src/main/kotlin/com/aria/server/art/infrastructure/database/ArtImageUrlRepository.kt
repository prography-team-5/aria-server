package com.aria.server.art.infrastructure.database

import com.aria.server.art.domain.art.ArtImageUrl
import org.springframework.data.jpa.repository.JpaRepository

interface ArtImageUrlRepository: JpaRepository<ArtImageUrl, Long>
