package com.aria.server.art.infrastructure.rest.dto

import com.aria.server.art.domain.member.PlatformType
import com.fasterxml.jackson.annotation.JsonProperty

// TODO response 중 emali은 꼭 받아야해서 nullable 처리 안해줬기 때문에, 프론트와 이에대해 논의
data class SignUpRequestDto(
    val platformType: PlatformType,
    val nickname: String,
    val accessToken: String,
    val refreshToken: String
    )

data class SignInRequestDto(
    val platformType: PlatformType,
    val accessToken: String,
    val refreshToken: String
)

data class KakaoUserInfoResponse(
    val id: Long,
    val properties: Properties,
    val kakaoAccount: KakaoAccount
) {
    data class Properties(
        val nickname: String,
        val profileImage: String?,
        val thumbnailImage: String?
    )

    data class KakaoAccount(
        val email: String,
        val gender: String?,
        val birthday: String?,
        val ageRange: String?
    )
}

data class NaverUserInfoResponse(
    val resultcode: String,
    val message: String,
    val response: Response
) {
    data class Response(
        val id: String?,
        val name: String?,
        val email: String,
        val gender: String?,
        val age: String?,
        val birthday: String?,
        val profile_image: String?
    )
}

data class AppleUserInfoResponse(
    val sub: String,
    val email: String,
    val email_verified: String?,
    val name: AppleUserName?,
    val is_private_email: Boolean?,
    val picture: String?,
    val updated_at: Long?
)

data class AppleUserName(
    val firstName: String?,
    val lastName: String?
)

