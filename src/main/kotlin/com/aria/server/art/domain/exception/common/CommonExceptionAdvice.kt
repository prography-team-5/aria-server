package com.aria.server.art.domain.exception.common

import com.aria.server.art.infrastructure.rest.response.Response.Companion.failure
import org.hibernate.annotations.common.util.impl.LoggerFactory
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class CommonExceptionAdvice {

    private val log = LoggerFactory.logger(javaClass)

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun illegalArgumentExceptionAdvice(e: IllegalArgumentException) {
        failure(e.message.toString())
    }

    @ExceptionHandler(UnsupportedServiceException::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun unsupportedServiceExceptionAdvice(e: UnsupportedServiceException) =
        failure("지원하지 않는 서비스 입니다.")

    @ExceptionHandler(UnauthorizedException::class)
    @ResponseStatus(UNAUTHORIZED)
    fun unauthorizedExceptionAdvice(e: UnauthorizedException) =
        failure("해당 사용자의 권한으로는 요청이 불가합니다.")

}