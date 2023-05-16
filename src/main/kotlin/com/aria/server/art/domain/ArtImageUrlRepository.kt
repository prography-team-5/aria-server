package com.aria.server.art.domain

import org.springframework.data.jpa.repository.JpaRepository

interface ArtImageUrlRepository: JpaRepository<ArtImageUrl, Long> {
}
