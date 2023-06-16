package com.aria.server.art.application.service

import com.aria.server.art.application.usecase.MemberReportService
import com.aria.server.art.domain.exception.report.AlreadyReportedMemberException
import com.aria.server.art.domain.report.MemberReport
import com.aria.server.art.infrastructure.database.MemberReportRepository
import org.springframework.stereotype.Service

@Service
class MemberReportServiceImpl (
    private val memberReportRepository: MemberReportRepository
): MemberReportService {

    override fun createMemberReport(memberReport: MemberReport) {
        if (memberReportRepository.existsByReporterIdAndReportedMemberId(memberReport.reporterId, memberReport.reportedMemberId)) {
            throw AlreadyReportedMemberException()
        } else {
            memberReportRepository.save(memberReport)
        }
    }
}