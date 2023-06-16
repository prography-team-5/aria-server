package com.aria.server.art.application.usecase

import com.aria.server.art.domain.report.MemberReport

interface MemberReportService {
    fun createMemberReport(memberReport: MemberReport)
}