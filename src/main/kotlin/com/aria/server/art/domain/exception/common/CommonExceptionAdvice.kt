package com.aria.server.art.domain.exception.common

import com.aria.server.art.infrastructure.exception.member.situation.*
import com.aria.server.art.infrastructure.rest.response.Response
import org.hibernate.annotations.common.util.impl.LoggerFactory
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CommonExceptionAdvice {

    private val log = LoggerFactory.logger(javaClass)

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun illegalArgumentExceptionAdvice(e: IllegalArgumentException) {
        log.info("IllegalArgumentException is " + e.message.toString())
        ResponseEntity(e.message.toString(), INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(UnsupportedServiceException::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun unsupportedServiceExceptionAdvice(e: UnsupportedServiceException) =
        Response("지원하지 않는 서비스 입니다.", null)

}