package com.aria.server.art.domain.exception.report

import com.aria.server.art.infrastructure.rest.response.Response
import org.hibernate.annotations.common.util.impl.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ReportExceptionAdvice {

    private val log = LoggerFactory.logger(javaClass)

    @ExceptionHandler(AlreadyReportedArtException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun alreadyReportedArtException(e: AlreadyReportedArtException) =
        Response.failure("이미 신고한 작품입니다.")

    @ExceptionHandler(AlreadyReportedMemberException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun alreadyReportedMemberException(e: AlreadyReportedMemberException) =
        Response.failure("이미 신고한 사용자입니다.")

}