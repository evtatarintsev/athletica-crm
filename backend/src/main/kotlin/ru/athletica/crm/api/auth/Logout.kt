package ru.athletica.crm.api.auth

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange

@RestController
@RequestMapping("/api/auth/logout")
class Logout {

    @PostMapping
    suspend fun logout(exchange: ServerWebExchange) {
        exchange.response.cookies.remove("access_token")
        exchange.response.cookies.remove("refresh_token")
    }
}