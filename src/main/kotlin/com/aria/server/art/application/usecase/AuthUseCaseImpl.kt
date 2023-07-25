package com.aria.server.art.application.usecase

import com.aria.server.art.config.jwt.JwtProvider
import com.aria.server.art.domain.exception.member.NoResponseBodyException
import com.aria.server.art.domain.member.Member
import com.aria.server.art.domain.member.PlatformType
import com.aria.server.art.domain.member.PlatformType.*
import com.aria.server.art.domain.member.Role
import com.aria.server.art.infrastructure.rest.controller.AuthUseCase
import com.aria.server.art.infrastructure.rest.dto.*
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpMethod.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Service
class AuthUseCaseImpl(
    private val memberService: MemberService,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtProvider: JwtProvider,
): AuthUseCase {

    val KAKAO_API = "https://kapi.kakao.com/v2/user/me"
    val NAVER_API = "https://openapi.naver.com/v1/nid/me"
    val APPLE_API = "https://appleid.apple.com/auth/userinfo"

    @Transactional
    override fun signUp(dto: SignUpRequestDto): TokenDto =
        getEmail(dto.accessToken, dto.platformType)
            .run {
                memberService.createMember(Member(this, dto.nickname, Role.ROLE_MEMBER, dto.platformType))
                jwtProvider.createTokenDto(getUserAuthentication(this))
            }

    @Transactional(readOnly = true)
    override fun signIn(dto: SignInRequestDto): TokenDto =
        getEmail(dto.accessToken, dto.platformType)
            .run {
                memberService.checkExistsMemberByEmail(this)
                jwtProvider.createTokenDto(getUserAuthentication(this))
            }

    private fun getEmail(accessToken: String, platformType: PlatformType): String =
        getSocialUrlAndResponseType(platformType)
            .run {
                getResponse(first, accessToken, second)
                    ?.let { getEmailFromResponse(platformType, it) }
                    ?:throw NoResponseBodyException()
            }

    private fun <T> getResponse(url: String, accessToken: String, responseType: Class<T>): T? {
        val httpHeaders = HttpHeaders()
        httpHeaders.setBearerAuth(accessToken)
        return RestTemplate().exchange(url, POST, HttpEntity<T>(httpHeaders), responseType).body
    }

    private fun getSocialUrlAndResponseType(platformType: PlatformType): Pair<String, Class<*>> =
        when (platformType) {
            KAKAO -> KAKAO_API to KakaoUserInfoResponse::class.java
            NAVER -> NAVER_API to NaverUserInfoResponse::class.java
            APPLE -> APPLE_API to AppleUserInfoResponse::class.java
        }

    private fun getEmailFromResponse(platformType: PlatformType, response: Any) =
        when (platformType) {
            KAKAO -> (response as KakaoUserInfoResponse).kakao_account.email
            NAVER -> (response as NaverUserInfoResponse).response.email
            APPLE -> (response as AppleUserInfoResponse).email
        }

    private fun getUserAuthentication(email: String): Authentication =
        authenticationManagerBuilder.getObject()
            .authenticate(UsernamePasswordAuthenticationToken(email, email))

}