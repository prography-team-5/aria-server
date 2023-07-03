package com.aria.server.art.domain.exception.sociallink

import com.aria.server.art.infrastructure.rest.response.Response.Companion.failure
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class SocialLinkExceptionAdvice {

    @ExceptionHandler(DuplicatedSocialLinkException::class)
    @ResponseStatus(CONFLICT)
    fun duplicatedSocialLinkException(e: DuplicatedSocialLinkException) =
        failure("이미 존재하는 소셜링크 입니다.")

    @ExceptionHandler(SocialLinkNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun socialLinkNotFoundException(e: SocialLinkNotFoundException) =
        failure("해당 소셜링크를 찾지 못했습니다.")
}
