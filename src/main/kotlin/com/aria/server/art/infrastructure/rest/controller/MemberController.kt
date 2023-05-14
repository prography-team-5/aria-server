package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.dto.*
import com.aria.server.art.infrastructure.rest.dto.SignInRequestDto
import com.aria.server.art.infrastructure.rest.dto.SignUpRequestDto
import com.aria.server.art.infrastructure.rest.dto.TokenDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@Tag(name = "Member", description = "Member API Document")
@RestController
@RequestMapping("/v1/members")
class MemberController(
    private val memberService: MemberService
) {

    @Operation(summary = "Sign up API")
    @PostMapping("/sign-up")
    @ResponseStatus(CREATED)
    fun signUp(@RequestBody request: SignUpRequestDto): ResponseEntity<TokenDto> =
         ResponseEntity(memberService.signUp(request), CREATED)


    @Operation(summary = "Sign in API")
    @PostMapping("/sign-in")
    @ResponseStatus(OK)
    fun signIn(@RequestBody request: SignInRequestDto): ResponseEntity<TokenDto> =
         ResponseEntity(memberService.signIn(request), CREATED)

}