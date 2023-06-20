package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.ReportArtRequestDto
import com.aria.server.art.infrastructure.rest.dto.ReportMemberRequestDto

interface ReportUseCase {
    fun reportArt(request: ReportArtRequestDto)
    fun reportMember(request: ReportMemberRequestDto)
}