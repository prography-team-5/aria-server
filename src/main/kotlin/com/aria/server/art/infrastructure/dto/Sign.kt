package com.aria.server.art.infrastructure.dto

import com.aria.server.art.domain.SignType
import com.fasterxml.jackson.annotation.JsonProperty

// 리스폰스들 중 emali은 nullable 하지 않게 해줘도 될까?
data class SignUpRequestDto(
    val signType: SignType,
    val nickname: String,
    val accessToken: String,
    val refreshToken: String
    )

data class SignInRequestDto(
    val signType: SignType,
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

data class KakaoUserInfoRequest(
    @JsonProperty("access_token")
    val accessToken: String
)

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
    val email: String?,
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

data class KakaoFailure(
    val msg: String,
    val code: Int
)

data class Failure(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)

