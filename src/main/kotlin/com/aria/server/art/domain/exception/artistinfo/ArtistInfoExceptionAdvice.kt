package com.aria.server.art.domain.exception.artistinfo

import com.aria.server.art.infrastructure.rest.response.Response.Companion.failure
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ArtistInfoExceptionAdvice {

    @ExceptionHandler(ArtistInfoAlreadyExistException::class)
    @ResponseStatus(CONFLICT)
    fun artistInfoAlreadyExistException(e: ArtistInfoAlreadyExistException) =
        failure("이미 작가 정보가 존재합니다.")

    @ExceptionHandler(ArtistInfoNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun artistInfoNotFoundException(e: ArtistInfoNotFoundException) =
        failure("해당 작가의 정보를 찾을 수 없습니다.")
}
