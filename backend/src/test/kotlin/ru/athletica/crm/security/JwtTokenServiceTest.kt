package ru.athletica.crm.security

import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

class JwtTokenServiceTest {

    @Test
    fun generateToken_ShouldNotThrowWeakKeyException() {
        // Arrange
        val jwtTokenService = JwtTokenService(3600, 86400)

        // Act
        val token = jwtTokenService.generateToken("testUser")

        // Assert
        assertNotNull(token, "Token should be generated successfully")
        println("[DEBUG_LOG] Generated token: $token")
    }

    @Test
    fun validateToken_ShouldReturnTrue_ForValidToken() {
        // Arrange
        val jwtTokenService = JwtTokenService(3600, 86400)
        val token = jwtTokenService.generateToken("testUser")

        // Act
        val isValid = jwtTokenService.validateToken(token)

        // Assert
        kotlin.test.assertTrue(isValid, "Token should be valid")
    }

    @Test
    fun getUsernameFromToken_ShouldReturnUsername_ForValidToken() {
        // Arrange
        val jwtTokenService = JwtTokenService(3600, 86400)
        val username = "testUser"
        val token = jwtTokenService.generateToken(username)

        // Act
        val extractedUsername = jwtTokenService.getUsernameFromToken(token)

        // Assert
        kotlin.test.assertEquals(username, extractedUsername, "Extracted username should match the original username")
    }
}