package com.aria.server.art.domain.exception.member

import com.aria.server.art.infrastructure.rest.response.Response.Companion.failure
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
        failure("소셜 서비스와 통신에 실패했습니다." + e.message!!)

    @ExceptionHandler(NoResponseBodyException::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun noResponseBodyExceptionAdvice(e: NoResponseBodyException) =
        failure("소셜 서비스로부터 사용자 정보를 받지 못했습니다.")

    @ExceptionHandler(DuplicatedNicknameException::class)
    @ResponseStatus(CONFLICT)
    fun duplicatedNicknameExceptionAdvice(e: DuplicatedNicknameException) =
        failure("이미 존재하는 닉네임입니다.")

    @ExceptionHandler(MemberNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun memberNotFoundExceptionAdvice(e: MemberNotFoundException) =
        failure("해당 " + e.message + "을 가진 사용자를 찾을 수 없습니다.")

    @ExceptionHandler(CurrentMemberNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun currentMemberNotFoundExceptionAdvice(e: CurrentMemberNotFoundException) =
        failure("현재 로그인한 사용자를 찾을 수 없습니다.")

    @ExceptionHandler(AlreadyArtistException::class)
    @ResponseStatus(BAD_REQUEST)
    fun alreadyArtistExceptionAdvice(e: AlreadyArtistException) =
            failure("이미 작가로 등록된 사용자입니다.")

}