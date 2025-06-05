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
        println("[DEBUG] JwtAuthenticationFilter processing request for path: ${path}")

        // Skip authentication for login endpoint
        if (path.startsWith("/api/login")) {
            println("[DEBUG] Skipping authentication for login endpoint")
            return chain.filter(exchange)
        }

        val authHeader = exchange.request.headers.getFirst("Authorization")
        println("[DEBUG] Authorization header: ${authHeader ?: "null"}")

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7)
            println("[DEBUG] Extracted token: ${token.take(15)}...")

            val isValid = jwtTokenService.validateToken(token)
            println("[DEBUG] Token validation result: $isValid")

            if (isValid) {
                val username = jwtTokenService.getUsernameFromToken(token)
                println("[DEBUG] Username from token: $username")

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
                    println("[DEBUG] Authentication successful for user: $username")
                    return chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
                }
            }
        }

        // Return 401 Unauthorized if authentication fails
        println("[DEBUG] JWT Authentication failed for path: ${path}")
        exchange.response.statusCode = org.springframework.http.HttpStatus.UNAUTHORIZED
        return exchange.response.setComplete()
    }
}
