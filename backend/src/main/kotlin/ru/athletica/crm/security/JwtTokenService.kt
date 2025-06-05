package ru.athletica.crm.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

@Service
class JwtTokenService(
    @Value("\${jwt.expiration:3600}") private val expiration: Long,
    @Value("\${jwt.refresh-expiration:86400}") private val refreshExpiration: Long
) {
    // Используем Keys.secretKeyFor для создания ключа достаточного размера для алгоритма HS512
    private val key: Key = Keys.secretKeyFor(SignatureAlgorithm.HS512)

    /**
     * Generate a short-lived authentication token
     */
    fun generateToken(username: String): String {
        return generateToken(username, expiration)
    }

    /**
     * Generate a long-lived refresh token
     */
    fun generateRefreshToken(username: String): String {
        return generateToken(username, refreshExpiration)
    }

    private fun generateToken(username: String, expirationTime: Long): String {
        val now = Date()
        val expiryDate = Date(now.time + expirationTime * 1000)

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    /**
     * Extract username from token
     */
    fun getUsernameFromToken(token: String): String? {
        return getClaimFromToken(token) { it.subject }
    }

    /**
     * Validate token
     */
    fun validateToken(token: String): Boolean {
        return try {
            val claims = getAllClaimsFromToken(token)
            !claims.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    private fun <T> getClaimFromToken(token: String, claimsResolver: (Claims) -> T): T? {
        return try {
            val claims = getAllClaimsFromToken(token)
            claimsResolver(claims)
        } catch (e: Exception) {
            null
        }
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }
}
