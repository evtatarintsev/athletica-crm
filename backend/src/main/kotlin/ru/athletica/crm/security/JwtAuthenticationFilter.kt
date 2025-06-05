package ru.athletica.crm.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import org.springframework.core.annotation.Order
import org.springframework.core.Ordered

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class JwtAuthenticationFilter(private val jwtTokenService: JwtTokenService) : WebFilter {

       override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val path = exchange.request.path.value()

        // Skip authentication for login endpoint
        if (path.startsWith("/api/login")) {
            return chain.filter(exchange)
        }

        // First try to get token from Authorization header
        val authHeader = exchange.request.headers.getFirst("Authorization")
        var token: String? = null

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7)
        } else {
            // If no Authorization header, try to get token from cookie
            val cookies = exchange.request.cookies["access_token"]
            if (!cookies.isNullOrEmpty()) {
                token = cookies[0].value
            }
        }

        if (token != null) {
            val isValid = jwtTokenService.validateToken(token)

            if (isValid) {
                val username = jwtTokenService.getUsernameFromToken(token)

                if (username != null) {
                    val user = User.builder()
                        .username(username)
                        .password("")
                        .authorities(emptyList())
                        .build()

                    val authentication = UsernamePasswordAuthenticationToken(
                        user, null, emptyList()
                    )

                    // Set the authentication in the security context and continue the filter chain
                    return chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
                }
            }
        }

        // Return 401 Unauthorized if authentication fails
        exchange.response.statusCode = org.springframework.http.HttpStatus.UNAUTHORIZED
        return exchange.response.setComplete()
    }
}
