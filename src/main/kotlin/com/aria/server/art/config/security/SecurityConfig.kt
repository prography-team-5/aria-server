package com.aria.server.art.config.security

import com.aria.server.art.config.jwt.JwtAccessDeniedHandler
import com.aria.server.art.config.jwt.JwtAuthenticationEntryPoint
import com.aria.server.art.config.jwt.JwtProvider
import com.aria.server.art.config.jwt.JwtSecurityConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.CorsFilter


@Configuration
class SecurityConfig (
    private val jwtProvider: JwtProvider,
    private val corsFilter: CorsFilter,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler
) {

    // 최종적으론 패스워드 없이 로그인, 현재는 Deprecated 문제해결 요
    @Bean
    fun passwordEncoder(): PasswordEncoder = NoOpPasswordEncoder.getInstance()

    @Bean
    @Throws(Exception::class)
    protected fun config(http: HttpSecurity): SecurityFilterChain =
         http
            .csrf().disable()
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)

            .and()
            .apply(JwtSecurityConfig(jwtProvider))

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeHttpRequests { authorize ->
                authorize
                    .shouldFilterAllDispatcherTypes(false)
                    .antMatchers("/swagger-ui/**", "/api-docs/**", "/v1/auth/sign-up", "/v1/auth/sign-in", "/api")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
             .build()
}
