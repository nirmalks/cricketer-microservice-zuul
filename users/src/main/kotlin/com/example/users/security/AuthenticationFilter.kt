package com.example.users.security

import com.example.users.LoginRequestModel
import com.example.users.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class AuthenticationFilter(
    @Autowired val usersService: UserService,
    val customEnvironment: Environment,
    @Autowired
    val authManager: AuthenticationManager
): UsernamePasswordAuthenticationFilter() {
    override fun attemptAuthentication(req: HttpServletRequest, resp: HttpServletResponse): Authentication {
        try {
            val credentials = ObjectMapper().registerModule(KotlinModule()).readValue(req.inputStream, LoginRequestModel::class.java)
            return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    credentials.email,
                    credentials.password,
                    listOf()
                )
            )
        } catch (ex: Exception) {
            throw RuntimeException()
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        val username = ((authResult?.principal) as User).username
        val user = usersService.getUserByEmail(username).block()
        val localDate = Date.from(
            LocalDateTime.now().plus(Duration.of(30, ChronoUnit.MINUTES))
                .atZone(ZoneId.systemDefault())
                .toInstant()
        )
        val jwtToken: String = Jwts.builder()
            .setSubject(user?.id)
            .setExpiration(localDate)
            .signWith(SignatureAlgorithm.HS256, "secret").compact()
        response.addHeader("token", jwtToken)
        response.addHeader("userId", user?.id)
    }
}