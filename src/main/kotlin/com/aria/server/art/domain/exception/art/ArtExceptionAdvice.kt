package com.aria.server.art.domain.exception.art

import com.aria.server.art.infrastructure.rest.response.Response
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ArtExceptionAdvice {

    @ExceptionHandler(ArtImageS3Exception::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun artImageS3ExceptionAdvice(e: ArtImageS3Exception) =
        Response("S3에 이미지 업로드를 실패했습니다.", null)

    @ExceptionHandler(ArtImageNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun artImageUrlNotFoundExceptionAdvice(e: ArtImageNotFoundException) =
        Response("해당 이미지를 찾을 수 없습니다.", null)
}
