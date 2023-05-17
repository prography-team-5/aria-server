package com.aria.server.art.domain.exception.follow

import com.aria.server.art.infrastructure.rest.response.Response
import org.hibernate.annotations.common.util.impl.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.RestClientException

@RestControllerAdvice
class FollowExceptionAdvice {

    private val log = LoggerFactory.logger(javaClass)

    @ExceptionHandler(FollowNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun restClientExceptionAdvice(e: FollowNotFoundException) =
        Response("팔로우 정보를 찾을 수 없습니다.", null)
}