package com.aria.server.art.domain.exception.member

import com.aria.server.art.infrastructure.rest.response.Response
import org.hibernate.annotations.common.util.impl.LoggerFactory
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.RestClientException

@RestControllerAdvice
class MemberExceptionAdvice {

    private val log = LoggerFactory.logger(javaClass)

    @ExceptionHandler(RestClientException::class)
    @ResponseStatus(UNAUTHORIZED)
    fun restClientExceptionAdvice(e: RestClientException) =
        Response("인증되지 않은 엑세스 토큰입니다.", null)

    @ExceptionHandler(UnsupportedFindTypeException::class)
    @ResponseStatus(BAD_REQUEST)
    fun unsupportedServiceExceptionAdvice(e: UnsupportedFindTypeException) =
        Response("해당 조건으로는 사용자를 찾을 수 없습니다", null)

    @ExceptionHandler(NoResponseBodyException::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun noResponseBodyExceptionAdvice(e: NoResponseBodyException) =
        Response("소셜 서비스로부터 사용자 정보를 받지 못했습니다.", null)

    @ExceptionHandler(DuplicatedNicknameException::class)
    @ResponseStatus(CONFLICT)
    fun duplicatedNicknameExceptionAdvice(e: DuplicatedNicknameException) =
        Response("이미 존재하는 닉네임입니다.", null)

    @ExceptionHandler(MemberNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun memberNotFoundExceptionAdvice(e: MemberNotFoundException) =
        Response("해당 " + e.message + "을 가진 사용자를 찾을 수 없습니다.", null)


}