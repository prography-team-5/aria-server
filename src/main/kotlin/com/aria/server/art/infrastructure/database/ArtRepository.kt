package com.aria.server.art.infrastructure.database

import com.aria.server.art.domain.art.Art
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ArtRepository: JpaRepository<Art, Long> {
    @Query(value = "SELECT * FROM art ORDER BY RAND() LIMIT :size", nativeQuery = true)
    fun findRandomArts(@Param("size") size: Int): List<Art>

    fun findSliceByMemberId(memberId: Long, pageable: Pageable): Slice<Art>
}
