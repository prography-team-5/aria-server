package com.aria.server.art.application.usecase

import com.aria.server.art.domain.report.ArtReport

interface ArtReportService {
    fun createArtReport(artReport: ArtReport)
}