package com.github.nenadjakic.eav.controller

import com.github.nenadjakic.eav.dto.*
import com.github.nenadjakic.eav.security.JwtService
import com.github.nenadjakic.eav.security.model.SecurityUser
import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
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
class EntityTypeControllerTest {

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
    @DisplayName("Get all entity types successfully.")
    fun findAll() {
        val response = restTemplate.exchange(
            "/entity-type",
            HttpMethod.GET,
            HttpEntity<String>(headers),
            List::class.java
        )

        assertEquals(200, response.statusCode.value())
        assertTrue(response.body.size > 10)
    }

    @Test
    @DisplayName("Get first page successfully.")
    fun findPage() {
        val response = restTemplate.exchange(
            "/entity-type/page?pageNumber={pageNumber}&pageSize={pageSize}",
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
    @DisplayName("Get by id where id does not exist.")
    fun findById_NotFound() {
        val response = restTemplate.exchange(
            "/entity-type/99999",
            HttpMethod.GET,
            HttpEntity<String>(headers),
            EntityTypeResponse::class.java
        )

        assertEquals(404, response.statusCode.value())
    }

    @Test
    @DisplayName("Get by id successfully.")
    fun findById() {
        val response = restTemplate.exchange(
            "/entity-type/10002",
            HttpMethod.GET,
            HttpEntity<String>(headers),
            EntityTypeResponse::class.java
        )

        assertEquals(200, response.statusCode.value())

        val documentContext: DocumentContext = JsonPath.parse(response.body)
        val entityTypeResponse = documentContext.json<EntityTypeResponse>()

        assertEquals(10002, entityTypeResponse.id)
        assertEquals("entity_type_2", entityTypeResponse.name)
        assertEquals("description_2", entityTypeResponse.description)
    }

    @Test
    @DisplayName("Create new entity type.")
    fun create() {
        val body = EntityTypeAddRequest()
        body.name = "new_name_added"
        body.description = "new_description"
        val postResponse = restTemplate.exchange<Void>(
            "/entity-type",
            HttpMethod.POST,
            HttpEntity(body, headers),
            EntityTypeAddRequest::class.java
        )
        assertEquals(201, postResponse.statusCode.value())

        val response = restTemplate.exchange(
            postResponse.headers.location,
            HttpMethod.GET,
            HttpEntity<String>(headers),
            EntityTypeResponse::class.java
        )
        assertEquals(200, response.statusCode.value())
    }

    @Test
    @DisplayName("Update existing entity type.")
    fun update() {
        val body = EntityTypeUpdateRequest()
        body.id = 10001
        body.name = "new_name_updated"
        body.description = "new_description"
        val putResponse = restTemplate.exchange(
            "/entity-type",
            HttpMethod.PUT,
            HttpEntity(body, headers),
            EntityTypeUpdateRequest::class.java
        )
        assertEquals(204, putResponse.statusCode.value())

        val response = restTemplate.exchange(
            "/entity-type/10001",
            HttpMethod.GET,
            HttpEntity<String>(headers),
            EntityTypeResponse::class.java
        )
        assertEquals(200, response.statusCode.value())

        val documentContext: DocumentContext = JsonPath.parse(response.body)
        val entityTypeResponse = documentContext.json<EntityTypeResponse>()
        assertEquals(10001, entityTypeResponse.id)
        assertEquals("new_name_updated", entityTypeResponse.name)
        assertEquals("new_description", entityTypeResponse.description)
    }

    @Test
    @DisplayName("Delete entity type successfully.")
    fun deleteById() {
        assertEquals(204, restTemplate.exchange(
            "/entity-type/10002",
            HttpMethod.DELETE,
            HttpEntity<String>(headers),
            Void::class.java
        ).statusCode.value())

        assertEquals(404, restTemplate.exchange(
            "/entity-type/10002",
            HttpMethod.GET,
            HttpEntity<String>(headers),
            EntityTypeResponse::class.java
        ).statusCode.value())
    }
}