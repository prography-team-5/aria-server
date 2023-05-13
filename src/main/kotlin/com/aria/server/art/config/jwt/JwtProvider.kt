package com.aria.server.art.config.jwt

import com.aria.server.art.infrastructure.rest.dto.TokenDto
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.hibernate.annotations.common.util.impl.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import java.util.stream.Collectors


@Component
class JwtProvider(@Value("\${jwt.secret}") secretKey: String) {
    private val key: Key
    private val log = LoggerFactory.logger(javaClass)
    companion object {
        private const val AUTHORITIES_KEY = "auth"
        private const val BEARER_TYPE = "bearer"
        private const val ACCESS_TOKEN_EXPIRE_TIME = (1000 * 60 * 60 * 24).toLong() // 1일
        private const val REFRESH_TOKEN_EXPIRE_TIME = (1000 * 60 * 60 * 24 * 7).toLong() // 7일
    }
    init {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }

    fun createTokenDto(authentication: Authentication): TokenDto =
         TokenDto(
             BEARER_TYPE,
             publishAccessToken(authentication.name, getAuthorities(authentication), Date(Date().time + ACCESS_TOKEN_EXPIRE_TIME)),
             publishRefreshToken(Date(Date().time + REFRESH_TOKEN_EXPIRE_TIME)))

    fun publishAccessToken(email: String, authorities: String, expireTime: Date): String =
         Jwts.builder()
            .setSubject(email)
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(expireTime)
            .compact()

    fun publishRefreshToken(expireTime: Date): String =
         Jwts.builder()
            .setExpiration(expireTime)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true
        } catch (e: MalformedJwtException) {
            log.info("잘못된 JWT 서명입니다.")
        } catch (e: ExpiredJwtException) {
            log.info("만료된 JWT 토큰입니다.")
        } catch (e: UnsupportedJwtException) {
            log.info("지원되지 않는 JWT 토큰입니다.")
        } catch (e: IllegalArgumentException) {
            log.info("JWT 토큰이 잘못되었습니다.")
        }
        return false
    }

    fun getAuthentication(accessToken: String): Authentication {
        val claims = parseClaims(accessToken)
        if (claims[AUTHORITIES_KEY] == null) {
            throw RuntimeException("권한 정보가 없는 토큰입니다.")
        }
        val authorities = getAuthorities(claims)
        return UsernamePasswordAuthenticationToken(User(claims.subject, "", authorities), "", authorities)
    }

    fun getAuthorities(claims: Claims): Collection<GrantedAuthority> =
        Arrays.stream(
            claims[AUTHORITIES_KEY].toString().split(",".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray())
            .map { role: String -> SimpleGrantedAuthority(role)}
            .collect(Collectors.toList())

    fun getAuthorities(authentication: Authentication): String =
        authentication.authorities.stream()
            .map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.joining(","))

    private fun parseClaims(accessToken: String): Claims =
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).body;
        } catch (e: ExpiredJwtException) {
            e.claims
        }

}