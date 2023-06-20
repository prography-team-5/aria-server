package com.aria.server.art.infrastructure.database

import com.aria.server.art.domain.report.MemberReport
import org.springframework.data.jpa.repository.JpaRepository

interface MemberReportRepository: JpaRepository<MemberReport, Long> {
    fun existsByReporterIdAndReportedMemberId(reporterId: Long, reportedMemberId: Long): Boolean
}