package ru.athletica.crm.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec
import java.security.MessageDigest

@Service
class JwtTokenService(
    @Value("\${jwt.expiration:3600}") val expiration: Long,
    @Value("\${jwt.refresh-expiration:86400}") val refreshExpiration: Long,
    @Value("\${jwt.secret:athleticaSecretKeyWithAtLeast32BytesLength}") private val secret: String
) {
    // Используем фиксированный ключ для алгоритма HS512
    // Для HS512 требуется ключ размером не менее 512 бит
    private val key: Key = generateKey(secret)

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
            val isValid = !claims.expiration.before(Date())
            isValid
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

    /**
     * Генерирует ключ для подписи JWT токенов на основе секрета
     * Для алгоритма HS512 требуется ключ размером не менее 512 бит (64 байта)
     */
    private fun generateKey(secret: String): Key {
        // Используем SHA-512 для получения 64-байтового хеша из секрета
        val md = MessageDigest.getInstance("SHA-512")
        val keyBytes = md.digest(secret.toByteArray())
        // Создаем ключ для алгоритма HS512
        return SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.jcaName)
    }
}
