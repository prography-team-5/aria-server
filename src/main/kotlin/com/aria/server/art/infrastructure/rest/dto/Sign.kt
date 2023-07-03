package com.aria.server.art.infrastructure.rest.dto

import com.aria.server.art.domain.member.PlatformType
import com.fasterxml.jackson.annotation.JsonProperty

// TODO response 중 emali은 꼭 받아야해서 nullable 처리 안해줬기 때문에, 프론트와 이에대해 논의
data class SignUpRequestDto(
    val platformType: PlatformType,
    val nickname: String,
    val accessToken: String,
    )

data class SignInRequestDto(
    val platformType: PlatformType,
    val accessToken: String,
)


data class KakaoUserInfoResponse(
    val id: Long,
    val connected_at: String,
    val properties: Properties,
    val kakao_account: KakaoAccount,
    ) {
    data class Properties(
        val nickname: String,
        val profile_image: String?,
        val thumbnail_image: String?
    )
    data class KakaoAccount(
        val profile_nickname_needs_agreement: Boolean,
        val profile_image_needs_agreement: Boolean,
        val profile: Profile,
        val has_email: Boolean,
        val email_needs_agreement: Boolean,
        val is_email_valid: Boolean,
        val is_email_verified: Boolean,
        val email: String
    ) {
        data class Profile(
            val nickname: String,
            val thumbnail_image_url: String?,
            val profile_image_url: String?,
            val is_dafault_image: Boolean
        )
    }
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

