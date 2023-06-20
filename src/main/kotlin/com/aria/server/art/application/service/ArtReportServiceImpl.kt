package com.aria.server.art.application.service

import com.aria.server.art.application.usecase.ArtReportService
import com.aria.server.art.domain.exception.report.AlreadyReportedArtException
import com.aria.server.art.domain.report.ArtReport
import com.aria.server.art.infrastructure.database.ArtReportRepository
import org.springframework.stereotype.Service

@Service
class ArtReportServiceImpl (
    private val artReportRepository: ArtReportRepository
): ArtReportService {

    override fun createArtReport(artReport: ArtReport) {
        if (artReportRepository.existsByReporterIdAndReportedArtId(artReport.reporterId, artReport.reportedArtId)) {
            throw AlreadyReportedArtException()
        } else {
            artReportRepository.save(artReport)
        }
    }
}