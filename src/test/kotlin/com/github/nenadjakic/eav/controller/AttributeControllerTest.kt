package com.github.nenadjakic.eav.controller

import com.github.nenadjakic.eav.dto.AttributeResponse
import com.github.nenadjakic.eav.security.JwtService
import com.github.nenadjakic.eav.security.model.SecurityUser
import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.test.context.TestPropertySource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations=["classpath:application.properties"])
class AttributeControllerTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var jwtService: JwtService

    lateinit var headers: HttpHeaders

    @BeforeEach
    fun setUp() {
        val securityUser = SecurityUser()
        securityUser.username = "test"
        securityUser.addAuthority(SimpleGrantedAuthority("READER"))
        securityUser.addAuthority(SimpleGrantedAuthority("WRITER"))
        val token = jwtService.createToken(securityUser)

        headers = HttpHeaders()
        headers.setBearerAuth(token)
    }

    @Test
    fun findAll() {
        val response = restTemplate.exchange(
            "/attribute",
            HttpMethod.GET,
            HttpEntity<String>(headers),
            List::class.java
        )

        assertEquals(200, response.statusCode.value())
        assertTrue(response.body.size > 10)
    }

    @Test
    fun findPage() {
        val response = restTemplate.exchange(
            "/attribute/page?pageNumber={pageNumber}&pageSize={pageSize}",
            HttpMethod.GET,
            HttpEntity<String>(headers),
            Any::class.java,
            0, 10
        )
        assertEquals(200, response.statusCode.value())
        assertEquals(10, (response.body as Map<*, *>)["size"])
        assertEquals(10, ((response.body as Map<*, *>)["content"] as Collection<*>).size)
    }

    @Test
    fun findById() {
        val response = restTemplate.exchange(
            "/attribute/10002",
            HttpMethod.GET,
            HttpEntity<String>(headers),
            AttributeResponse::class.java
        )

        assertEquals(200, response.statusCode.value())

        val documentContext: DocumentContext = JsonPath.parse(response.body)
        val attributeResponse = documentContext.json<AttributeResponse>()

        assertEquals(10002, attributeResponse.id)
        assertEquals("attribute_2", attributeResponse.name)
        assertEquals("description_2", attributeResponse.description)
    }

    @Test
    fun deleteById() {
    }

    @Test
    fun update() {
    }

    @Test
    fun create() {
    }
}