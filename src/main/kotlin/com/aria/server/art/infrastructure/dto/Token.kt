package com.aria.server.art.infrastructure.dto


data class TokenDto(
    private val grantType: String,
    private val accessToken: String,
    private val refreshToken: String,
   )