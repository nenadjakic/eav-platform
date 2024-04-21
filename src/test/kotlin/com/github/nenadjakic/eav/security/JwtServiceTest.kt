package com.github.nenadjakic.eav.security

import com.github.nenadjakic.eav.security.model.SecurityUser
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@TestPropertySource(locations=["classpath:application.properties"])
class JwtServiceTest {

    @Autowired
    lateinit var jwtService: JwtService
    lateinit var token: String

    @BeforeEach
    fun setUp() {
        val securityUser = SecurityUser()
        securityUser.username = "test"
        securityUser.addAuthority(SimpleGrantedAuthority("ROLE_1"))
        token = jwtService.createToken(securityUser)
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun createToken() {
        assertNotEquals("", token)
    }

    @Test
    fun extractAllClaims() {
        val claims = jwtService.extractAllClaims(token)
        assertEquals(4, claims.size)
        assertNotNull(claims.subject)
        assertNotNull(claims["roles"])
        assertEquals(1, (claims["roles"] as List<*>).size)
    }

    @Test
    fun extractUserName() {
        val extractUserNameMethod = JwtService::class.java.getDeclaredMethod("extractUserName", String::class.java)
        extractUserNameMethod.isAccessible = true
        val userClaim: String = extractUserNameMethod.invoke(jwtService, token).toString()
        assertEquals("test", userClaim)
    }

    @DisplayName("Unit test when jwt token is valid.")
    @Test
    fun isValid() {
        val validationResult = jwtService.isValid(token)
        assertEquals(true, validationResult)
    }
    @DisplayName("Unit test when jwt token is not valid.")
    @Test
    fun isValid_InvalidToken() {
        val validationResult = jwtService.isValid("INVALID")
        assertEquals(false, validationResult)
    }
}