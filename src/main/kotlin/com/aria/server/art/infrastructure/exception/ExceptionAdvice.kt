package com.aria.server.art.infrastructure.exception

import com.aria.server.art.infrastructure.exception.situation.NoResponseBodyException
import com.aria.server.art.infrastructure.exception.situation.UnsupportedServiceException
import org.hibernate.annotations.common.util.impl.LoggerFactory
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.RestClientException

@RestControllerAdvice
class ExceptionAdvice {

    private val log = LoggerFactory.logger(javaClass)

//    @ExceptionHandler(IllegalArgumentException::class)
//    @ResponseStatus(INTERNAL_SERVER_ERROR)
//    fun illegalArgumentExceptionAdvice(e: IllegalArgumentException) {
//        ResponseEntity.internalServerError()
//    }

    @ExceptionHandler(RestClientException::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun restClientExceptionAdvice(e: RestClientException) =
        ResponseEntity.status(INTERNAL_SERVER_ERROR)

    @ExceptionHandler(UnsupportedServiceException::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun unsupportedServiceExceptionAdvice(e: UnsupportedServiceException) =
        ResponseEntity.status(INTERNAL_SERVER_ERROR)

    @ExceptionHandler(NoResponseBodyException::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun noResponseBodyExceptionAdvice(e: NoResponseBodyException) =
        ResponseEntity.status(INTERNAL_SERVER_ERROR)

}