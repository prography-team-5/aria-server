package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.ReportArtRequestDto
import com.aria.server.art.infrastructure.rest.dto.ReportMemberRequestDto
import com.aria.server.art.infrastructure.rest.response.Response
import com.aria.server.art.infrastructure.rest.response.Response.Companion.success
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Report", description = "Report API Document")
@RestController
@RequestMapping("/v1/reports")
class ReportController (
    private val reportUseCase: ReportUseCase
) {

    @Operation(summary = "Report Art API")
    @PostMapping("/arts")
    fun reportArt(@RequestBody request: ReportArtRequestDto): ResponseEntity<Unit> {
        reportUseCase.reportArt(request)
        return ResponseEntity(CREATED)
    }

    @Operation(summary = "Report Member API")
    @PostMapping("/members")
    fun reportMember(@RequestBody request: ReportMemberRequestDto): ResponseEntity<Unit> {
        reportUseCase.reportMember(request)
        return ResponseEntity(CREATED)
    }


}