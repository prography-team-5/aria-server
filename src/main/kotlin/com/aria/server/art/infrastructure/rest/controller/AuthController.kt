package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.application.usecase.AuthUseCaseImpl
import com.aria.server.art.infrastructure.rest.dto.SignInRequestDto
import com.aria.server.art.infrastructure.rest.dto.SignUpRequestDto
import com.aria.server.art.infrastructure.rest.dto.TokenDto
import com.aria.server.art.infrastructure.rest.response.Response
import com.aria.server.art.infrastructure.rest.response.Response.Companion.success
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "Auth", description = "Auth API Document")
@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val authUseCaseImpl: AuthUseCaseImpl
) {

    @Operation(summary = "Sign up API")
    @PostMapping("/sign-up")
    fun signUp(@RequestBody request: SignUpRequestDto): ResponseEntity<TokenDto> =
        ResponseEntity(authUseCaseImpl.signUp(request), CREATED)

    @Operation(summary = "Sign in API")
    @PostMapping("/sign-in")
    fun signIn(@RequestBody request: SignInRequestDto): ResponseEntity<TokenDto> =
        ResponseEntity(authUseCaseImpl.signIn(request), CREATED)

}
