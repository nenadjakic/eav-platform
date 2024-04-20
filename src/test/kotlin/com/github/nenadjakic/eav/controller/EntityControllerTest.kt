package com.github.nenadjakic.eav.controller

import com.github.nenadjakic.eav.dto.EntityAddRequest
import com.github.nenadjakic.eav.dto.EntityResponse
import com.github.nenadjakic.eav.dto.EntityUpdateRequest
import com.github.nenadjakic.eav.security.JwtService
import com.github.nenadjakic.eav.security.model.SecurityUser
import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.test.context.TestPropertySource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations=["classpath:application.properties"])
class EntityControllerTest {

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

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun findAll() {
        val response = restTemplate.exchange(
            "/entity",
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
            "/entity/page?pageNumber={pageNumber}&pageSize={pageSize}",
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
    fun findById_NotFound() {
        val response = restTemplate.exchange(
            "/entity/99999",
            HttpMethod.GET,
            HttpEntity<String>(headers),
            EntityResponse::class.java
        )

        assertEquals(404, response.statusCode.value())
    }

    @Test
    fun findById() {
        val response = restTemplate.exchange(
            "/entity/10002",
            HttpMethod.GET,
            HttpEntity<String>(headers),
            EntityResponse::class.java
        )

        assertEquals(200, response.statusCode.value())

        val documentContext: DocumentContext = JsonPath.parse(response.body)
        val entityResponse = documentContext.json<EntityResponse>()

        assertEquals(10002, entityResponse.id)
    }

    @Test
    fun create() {
        val body = EntityAddRequest()
        body.entityTypeId = 10001

        val postResponse = restTemplate.exchange(
            "/entity",
            HttpMethod.POST,
            HttpEntity(body, headers),
            EntityAddRequest::class.java
        )
        assertEquals(201, postResponse.statusCode.value())

        val response = restTemplate.exchange(
            postResponse.headers.location,
            HttpMethod.GET,
            HttpEntity<String>(headers),
            EntityResponse::class.java
        )
        assertEquals(200, response.statusCode.value())
    }

    @Test
    fun update() {
        val body = EntityUpdateRequest()
        body.id = 10001
        body.name = "new_name_updated"
        body.description = "new_description"
        val putResponse = restTemplate.exchange(
            "/entity",
            HttpMethod.PUT,
            HttpEntity(body, headers),
            EntityResponse::class.java
        )
        assertEquals(204, putResponse.statusCode.value())

        val response = restTemplate.exchange(
            "/entity/10001",
            HttpMethod.GET,
            HttpEntity<String>(headers),
            EntityResponse::class.java
        )
        assertEquals(200, response.statusCode.value())

        val documentContext: DocumentContext = JsonPath.parse(response.body)
        val entityResponse = documentContext.json<EntityResponse>()
        assertEquals(10001, entityResponse.id)
    }

    @Test
    fun deleteById() {
        assertEquals(204, restTemplate.exchange(
            "/entity/10002",
            HttpMethod.DELETE,
            HttpEntity<String>(headers),
            Void::class.java
        ).statusCode.value())
        assertEquals(404, restTemplate.exchange(
            "/entity/10002",
            HttpMethod.GET,
            HttpEntity<String>(headers),
            EntityResponse::class.java
        ).statusCode.value())
    }
}