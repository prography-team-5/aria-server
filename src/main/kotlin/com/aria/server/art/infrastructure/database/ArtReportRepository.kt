package com.aria.server.art.infrastructure.database

import com.aria.server.art.domain.report.ArtReport
import org.springframework.data.jpa.repository.JpaRepository

interface ArtReportRepository: JpaRepository<ArtReport, Long> {
    fun existsByReporterIdAndReportedArtId(reporterId: Long, reportedArtId: Long): Boolean
}