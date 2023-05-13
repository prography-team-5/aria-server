package com.aria.server.art.infrastructure.rest.dto


data class TokenDto(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String,
   )