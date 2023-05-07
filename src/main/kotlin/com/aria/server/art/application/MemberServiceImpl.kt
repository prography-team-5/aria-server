package com.aria.server.art.application

import com.aria.server.art.config.jwt.JwtProvider
import com.aria.server.art.domain.Member
import com.aria.server.art.domain.MemberRepository
import com.aria.server.art.domain.Role
import com.aria.server.art.domain.SignType
import com.aria.server.art.domain.SignType.*
import com.aria.server.art.infrastructure.MemberService
import com.aria.server.art.infrastructure.dto.*
import com.aria.server.art.infrastructure.exception.situation.MemberNotFoundException
import com.aria.server.art.infrastructure.exception.situation.NoResponseBodyException
import org.hibernate.annotations.common.util.impl.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.GET
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate


@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtProvider: JwtProvider,
): MemberService {

    private val log = LoggerFactory.logger(javaClass)

    @Transactional
    override fun signUp(dto: SignUpRequestDto): TokenDto {
        val email = getEmailFromSocialPlatform(dto.accessToken, dto.signType)
        memberRepository.save(Member(email, dto.nickname, "basic.jpg", Role.ROLE_MEMBER, dto.signType))
        return jwtProvider.createTokenDto(getUserAuthentication(email))
    }

    override fun signIn(dto: SignInRequestDto): TokenDto =
        getEmailFromSocialPlatform(dto.accessToken, dto.signType)
            .takeIf(memberRepository::existsByEmail)
            ?.let { jwtProvider.createTokenDto(getUserAuthentication(it)) }
            ?:throw MemberNotFoundException()

    fun getEmailFromSocialPlatform(accessToken: String, signType: SignType): String {
        val(url, responseType) = getSocialUrlAndResponseType(signType)
        val response = RestTemplate().exchange(url, GET, null, KakaoUserInfoResponse::class.java)
        log.info(response.toString())
        return RestTemplate()
            .run {
                exchange(url, GET, HttpEntity(HttpHeaders().setBearerAuth(accessToken)), responseType).body
                    ?.let { getEmailFromResponse(signType, it) }
                    ?:throw NoResponseBodyException()
            }
    }

    fun getSocialUrlAndResponseType(signType: SignType): Pair<String, Class<*>> =
        when (signType) {
            KAKAO -> "https://kapi.kakao.com/v2/user/me" to KakaoUserInfoResponse::class.java
            NAVER -> "https://openapi.naver.com/v1/nid/me" to NaverUserInfoResponse::class.java
            APPLE -> "https://appleid.apple.com/auth/userinfo" to AppleUserInfoResponse::class.java
        }

    fun getEmailFromResponse(signType: SignType, response: Any) =
        when (signType) {
            KAKAO -> (response as KakaoUserInfoResponse).kakaoAccount.email
            NAVER -> (response as NaverUserInfoResponse).response.email
            APPLE -> (response as AppleUserInfoResponse).email
        }

    fun getUserAuthentication(email: String): Authentication =
         authenticationManagerBuilder.getObject()
             .authenticate(UsernamePasswordAuthenticationToken(email, email))


     override fun getMemberInfo(email: String) =
         memberRepository.findByEmail(email)
             ?: throw MemberNotFoundException()


    override fun getMemberInfo(id: Long) =
        memberRepository.findByIdOrNull(id)
            ?: throw MemberNotFoundException()

}