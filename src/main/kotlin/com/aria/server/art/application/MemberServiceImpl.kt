package com.aria.server.art.application

import com.aria.server.art.config.jwt.JwtProvider
import com.aria.server.art.domain.member.*
import com.aria.server.art.domain.member.PlatformType.*
import com.aria.server.art.domain.member.Role.*
import com.aria.server.art.infrastructure.MemberService
import com.aria.server.art.infrastructure.dto.*
import com.aria.server.art.infrastructure.exception.member.situation.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.POST
import org.springframework.http.converter.HttpMessageConversionException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate


@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtProvider: JwtProvider,
): MemberService {
    val KAKAO_API = "https://kapi.kakao.com/v2/user/me"
    val NAVER_API = "https://openapi.naver.com/v1/nid/me"
    val APPLE_API = "https://appleid.apple.com/auth/userinfo"
    val BASIC_IMAGE = "basic.jpg"

    fun getMemberById(id: Long) =
        memberRepository.findByIdOrNull(id)
            ?:throw MemberNotFoundException("ID")

    fun getMemberByEmail(email: String) =
        memberRepository.findByEmail(email)
            ?:throw MemberNotFoundException("이메일")

    fun getMemberByNickname(nickname: String) =
        memberRepository.findByNickname(nickname)
            ?:throw MemberNotFoundException("닉네임")

    @Transactional
    override fun signUp(dto: SignUpRequestDto): TokenDto =
        if (!memberRepository.existsByNickname(dto.nickname)) {
            getEmail(dto.accessToken, dto.platformType)
                .run {
                    memberRepository.save(Member(this, dto.nickname, BASIC_IMAGE, ROLE_MEMBER, dto.platformType))
                    jwtProvider.createTokenDto(getUserAuthentication(this))
                }
        } else {
            throw DuplicatedNicknameException()
        }

    override fun signIn(dto: SignInRequestDto): TokenDto =
        getEmail(dto.accessToken, dto.platformType)
            .run {
                if (memberRepository.existsByEmail(this)) {
                    jwtProvider.createTokenDto(getUserAuthentication(this))
                } else {
                    throw MemberNotFoundException("이메일")
                }
            }

    fun getEmail(accessToken: String, platformType: PlatformType): String =
        getSocialUrlAndResponseType(platformType)
            .run {
                getResponse(first, accessToken, second, platformType)
                    ?.let { getEmailFromResponse(platformType, it) }
                    ?:throw NoResponseBodyException()
            }

    fun <T> getResponse(url: String, accessToken: String, responseType: Class<T>, platformType: PlatformType) =
        RestTemplate()
            .run {
                try {
                    exchange(url, POST, HttpEntity(HttpHeaders().setBearerAuth(accessToken)), responseType).body
                } catch (e: RestClientException) {
                    throw AccessTokenUnauthorizedException()
                } catch (e: Exception) {
                    throw SocialPlatformConnectionException()
                }
            }

    fun getSocialUrlAndResponseType(platformType: PlatformType): Pair<String, Class<*>> =
        when (platformType) {
            KAKAO -> KAKAO_API to KakaoUserInfoResponse::class.java
            NAVER -> NAVER_API to NaverUserInfoResponse::class.java
            APPLE -> APPLE_API to AppleUserInfoResponse::class.java
        }

    fun getEmailFromResponse(platformType: PlatformType, response: Any) =
        when (platformType) {
            KAKAO -> (response as KakaoUserInfoResponse).kakaoAccount.email
            NAVER -> (response as NaverUserInfoResponse).response.email
            APPLE -> (response as AppleUserInfoResponse).email
        }

    fun getCurrentMember() {
        getMemberByEmail(SecurityContextHolder.getContext().authentication.name)
    }

    fun getUserAuthentication(email: String): Authentication =
         authenticationManagerBuilder.getObject()
             .authenticate(UsernamePasswordAuthenticationToken(email, email))


}