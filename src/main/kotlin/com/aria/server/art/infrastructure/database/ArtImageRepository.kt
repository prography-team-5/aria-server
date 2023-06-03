package com.aria.server.art.infrastructure.database

import com.aria.server.art.domain.art.ArtImage
import org.springframework.data.jpa.repository.JpaRepository

interface ArtImageRepository: JpaRepository<ArtImage, Long>
