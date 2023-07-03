package com.aria.server.art.application.usecase

import com.aria.server.art.infrastructure.rest.controller.ReportUseCase
import com.aria.server.art.infrastructure.rest.dto.ReportArtRequestDto
import com.aria.server.art.infrastructure.rest.dto.ReportMemberRequestDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReportUseCaseImpl (
    private val artReportService: ArtReportService,
    private val memberReportService: MemberReportService,
    private val memberService: MemberService
): ReportUseCase {

    @Transactional
    override fun reportArt(request: ReportArtRequestDto) {
        val reporterId = memberService.getCurrentMember().id
        val artReport = request.toEntity(reporterId)
        artReportService.createArtReport(artReport)
    }

    @Transactional
    override fun reportMember(request: ReportMemberRequestDto) {
        val reporterId = memberService.getCurrentMember().id
        val memberReport = request.toEntity(reporterId)
        memberReportService.createMemberReport(memberReport)
    }
}