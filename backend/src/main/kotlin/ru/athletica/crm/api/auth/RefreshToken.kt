package ru.athletica.crm.api.auth

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import ru.athletica.api.schemas.LoginResponse
import ru.athletica.api.schemas.RefreshTokenRequest
import ru.athletica.crm.db.suspendTransaction
import ru.athletica.crm.security.JwtTokenService

@RestController
@RequestMapping("/api/auth/refresh-token")
class RefreshToken(private val jwtTokenService: JwtTokenService) {

    @PostMapping
    suspend fun refreshToken(@RequestBody request: RefreshTokenRequest?, exchange: ServerWebExchange): LoginResponse =
        suspendTransaction {
            // Get refresh token from request body or cookie
            val refreshToken = request?.refreshToken ?: exchange.request.cookies.getFirst("refresh_token")?.value
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token is required")

            // Validate refresh token
            if (!jwtTokenService.validateToken(refreshToken)) {
                throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired refresh token")
            }

            // Extract username from refresh token
            val username = jwtTokenService.getUsernameFromToken(refreshToken)
                ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token")

            // Generate new tokens
            val newToken = jwtTokenService.generateToken(username)
            val newRefreshToken = jwtTokenService.generateRefreshToken(username)

            // Set cookies
            val refreshCookie = ResponseCookie.from("refresh_token", newRefreshToken)
                .path("/")
                .httpOnly(true)
                .maxAge(jwtTokenService.refreshExpiration)
                .secure(true)
                .sameSite("Strict")
                .build()
            exchange.response.cookies.add("refresh_token", refreshCookie)

            val accessCookie = ResponseCookie.from("access_token", newToken)
                .path("/")
                .httpOnly(true)
                .maxAge(jwtTokenService.expiration)
                .secure(true)
                .sameSite("Strict")
                .build()
            exchange.response.cookies.add("access_token", accessCookie)

            // Return new tokens
            LoginResponse(token = newToken, refreshToken = newRefreshToken)
        }
}