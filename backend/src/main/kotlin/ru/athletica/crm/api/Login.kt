package ru.athletica.crm.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import ru.athletica.api.schemas.LoginRequest
import ru.athletica.api.schemas.LoginResponse
import ru.athletica.crm.db.suspendTransaction
import ru.athletica.crm.security.JwtTokenService


@RestController
@RequestMapping("/api/login")
class Login(private val jwtTokenService: JwtTokenService) {

    // In a real application, this would be replaced with a database lookup
    private val validUsers = mapOf(
        "admin" to "password",
        "user" to "password"
    )

    @PostMapping
    suspend fun login(@RequestBody request: LoginRequest, exchange: ServerWebExchange): LoginResponse = suspendTransaction {
        // Validate credentials
        if (!validUsers.containsKey(request.login) || validUsers[request.login] != request.password) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials")
        }

        val token = jwtTokenService.generateToken(request.login)
        val refreshToken = jwtTokenService.generateRefreshToken(request.login)

        val refreshCookie = ResponseCookie.from("refresh_token", refreshToken)
            .path("/")
            .httpOnly(true)
            .maxAge(jwtTokenService.refreshExpiration)
            .secure(true)
            .sameSite("Strict")
            .build()
        exchange.response.cookies.add("refresh_token", refreshCookie)

        val accessCookie = ResponseCookie.from("refresh_token", refreshToken)
            .path("/")
            .httpOnly(true)
            .maxAge(jwtTokenService.expiration)
            .secure(true)
            .sameSite("Strict")
            .build()
        exchange.response.cookies.add("access_token", accessCookie)

        LoginResponse(token = token, refreshToken = refreshToken)
    }
}
