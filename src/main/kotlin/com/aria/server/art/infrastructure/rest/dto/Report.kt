package com.aria.server.art.infrastructure.rest.dto

import com.aria.server.art.domain.report.ArtReport
import com.aria.server.art.domain.report.MemberReport

data class ReportArtRequestDto (
    val reportedArtId: Long,
    val content: String
) {
    fun toEntity(reporterId: Long): ArtReport =
        ArtReport(reporterId, reportedArtId, content)

}

data class ReportMemberRequestDto (
    val reportedMemberId: Long,
    val content: String
) {
    fun toEntity(reporterId: Long): MemberReport =
        MemberReport(reporterId, reportedMemberId, content)
}
