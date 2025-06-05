package ru.athletica.crm.api.auth

import org.springframework.http.ResponseCookie
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange

@RestController
@RequestMapping("/api/auth/logout")
class Logout {

    @PostMapping
    suspend fun logout(exchange: ServerWebExchange) {
        // Create expired cookies with the same properties to clear them in the browser
        val accessCookie = ResponseCookie.from("access_token", "")
            .path("/")
            .httpOnly(true)
            .maxAge(0)
            .secure(true)
            .sameSite("Strict")
            .build()
        exchange.response.cookies.add("access_token", accessCookie)

        val refreshCookie = ResponseCookie.from("refresh_token", "")
            .path("/")
            .httpOnly(true)
            .maxAge(0)
            .secure(true)
            .sameSite("Strict")
            .build()
        exchange.response.cookies.add("refresh_token", refreshCookie)
    }
}
