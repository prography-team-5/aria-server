package com.aria.server.art.application

import com.aria.server.art.config.jwt.JwtProvider
import com.aria.server.art.domain.member.*
import com.aria.server.art.domain.member.FindType.*
import com.aria.server.art.domain.member.SignType.*
import com.aria.server.art.infrastructure.MemberService
import com.aria.server.art.infrastructure.dto.*
import com.aria.server.art.infrastructure.exception.member.situation.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.GET
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate


@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtProvider: JwtProvider,
): MemberService {

    @Transactional(readOnly = true)
    override fun <T> getMember(param: T, findType: FindType): Member =
        when (findType) {
            ID -> memberRepository.findByIdOrNull(param as Long)?:throw MemberWithIdNotFoundException()
            EMAIL -> memberRepository.findByEmail(param as String)?:throw MemberWithEmailNotFoundException()
            NICKNAME -> memberRepository.findByNickname(param as String)?:throw MemberWithNicknameNotFoundException()
            else -> throw UnsupportedFindTypeException()
        }

    @Transactional(readOnly = true)
    override fun <T> checkDuplicatedMember(param: T, findType: FindType): Boolean =
        when (findType) {
            EMAIL -> memberRepository.existsByEmail(param as String)
            NICKNAME -> memberRepository.existsByNickname(param as String)
            else -> throw UnsupportedFindTypeException()
        }

    @Transactional
    override fun signUp(dto: SignUpRequestDto): TokenDto =
        if (!checkDuplicatedMember(dto.nickname, NICKNAME)) {
            getEmailFromSocialPlatform(dto.accessToken, dto.signType)
                .run {
                    memberRepository.save(Member(this, dto.nickname, "basic.jpg", Role.ROLE_MEMBER, dto.signType))
                    jwtProvider.createTokenDto(getUserAuthentication(this))
                }
        } else {
            throw DuplicatedNicknameException()
        }

    override fun signIn(dto: SignInRequestDto): TokenDto =
        getEmailFromSocialPlatform(dto.accessToken, dto.signType)
            .run {
                if (checkDuplicatedMember(this, EMAIL)) {
                    jwtProvider.createTokenDto(getUserAuthentication(this))
                } else {
                    throw MemberWithEmailNotFoundException()
                }
            }

    // TODO RestTemplate 에서 일어날 수 있는 에러를 유형별로 잡아서 ExceptionAdvice 에서 처리해야 합니다
    // TODO IllegalArgumentException 을 특히 잡아야 합니다
    fun getEmailFromSocialPlatform(accessToken: String, signType: SignType): String =
        getSocialUrlAndResponseType(signType)
            .run {
                RestTemplate()
                    .run {
                        exchange(first, GET, HttpEntity(HttpHeaders().setBearerAuth(accessToken)), second).body
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

    fun getCurrentMember() {
        getMember(SecurityContextHolder.getContext().authentication.name, EMAIL)
    }

    fun getUserAuthentication(email: String): Authentication =
         authenticationManagerBuilder.getObject()
             .authenticate(UsernamePasswordAuthenticationToken(email, email))


}