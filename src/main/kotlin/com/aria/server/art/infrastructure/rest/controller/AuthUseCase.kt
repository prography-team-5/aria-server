package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.SignInRequestDto
import com.aria.server.art.infrastructure.rest.dto.SignUpRequestDto
import com.aria.server.art.infrastructure.rest.dto.TokenDto

interface AuthUseCase {
    fun signUp(dto: SignUpRequestDto): TokenDto
    fun signIn(dto: SignInRequestDto): TokenDto
}