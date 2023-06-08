package com.aria.server.art.domain.exception.follow

import com.aria.server.art.infrastructure.rest.response.Response.Companion.failure
import org.hibernate.annotations.common.util.impl.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class FollowExceptionAdvice {

    private val log = LoggerFactory.logger(javaClass)

    @ExceptionHandler(FollowNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun followNotFoundExceptionAdvice(e: FollowNotFoundException) =
        failure("팔로우 정보를 찾을 수 없습니다.")

    @ExceptionHandler(AlreadyFollowException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun alreadyFollowException(e: AlreadyFollowException) =
        failure("이미 팔로우한 사용자입니다.")

    @ExceptionHandler(NoMoreFollowException::class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun noMoreFollowException(e: NoMoreFollowException) =
        failure("더 이상 가져올 수 있는 팔로우 정보가 없습니다.")
}