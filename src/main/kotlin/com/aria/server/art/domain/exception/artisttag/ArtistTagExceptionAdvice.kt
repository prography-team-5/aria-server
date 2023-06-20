package com.aria.server.art.domain.exception.artisttag

import com.aria.server.art.infrastructure.rest.response.Response.Companion.failure
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ArtistTagExceptionAdvice {

    @ExceptionHandler(DuplicatedArtistTagException::class)
    @ResponseStatus(CONFLICT)
    fun duplicatedArtistTagException(e: DuplicatedArtistTagException) =
        failure("이미 존재하는 작가 태그 입니다.")
}
