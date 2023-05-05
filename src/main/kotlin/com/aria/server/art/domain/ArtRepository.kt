package com.aria.server.art.domain

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ArtRepository: CrudRepository<Art, Long> {
    @Query(value = "SELECT * FROM art ORDER BY RAND() LIMIT 1", nativeQuery = true)
    fun findRandomArt(): Art
}
