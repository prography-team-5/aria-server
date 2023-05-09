package com.aria.server.art.infrastructure.exception.member

import com.aria.server.art.infrastructure.exception.member.situation.*
import com.aria.server.art.infrastructure.response.Response
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
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun restClientExceptionAdvice(e: RestClientException) =
        Response("소셜 서비스와 연결에 실패했습니다.", null)

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

    @ExceptionHandler(MemberWithNicknameNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun memberWithNicknameNotFoundExceptionAdvice(e: MemberWithNicknameNotFoundException) =
        Response("해당 닉네임을 가진 사용자를 찾을 수 없습니다.", null)

    @ExceptionHandler(MemberWithEmailNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun memberWithEmailNotFoundExceptionAdvice(e: MemberWithEmailNotFoundException) =
        Response("해당 이메일을 가진 사용자를 찾을 수 없습니다.", null)

    @ExceptionHandler(MemberWithIdNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun memberWithIdNotFoundExceptionAdvice(e: MemberWithIdNotFoundException) =
        Response("해당 ID를 가진 사용자를 찾을 수 없습니다.", null)
}