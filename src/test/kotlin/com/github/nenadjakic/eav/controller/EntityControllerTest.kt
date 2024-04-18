package com.github.nenadjakic.eav.controller

import com.github.nenadjakic.eav.dto.EntityAddRequest
import com.github.nenadjakic.eav.dto.EntityResponse
import com.github.nenadjakic.eav.dto.EntityUpdateRequest
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
import org.springframework.test.context.TestPropertySource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations=["classpath:application.properties"])
class EntityControllerTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun findAll() {
        val response = restTemplate.getForEntity("/entity", List::class.java)

        assertEquals(200, response.statusCode.value())
        assertTrue(response.body.size > 10)
    }

    @Test
    fun findPage() {
        val response = restTemplate.getForEntity("/entity/page?pageNumber={pageNumber}&pageSize={pageSize}", Any::class.java, 0, 10)
        assertEquals(200, response.statusCode.value())
        assertEquals(10, (response.body as Map<*, *>)["size"])
        assertEquals(10, ((response.body as Map<*, *>)["content"] as Collection<*>).size)
    }

    @Test
    fun findById_NotFound() {
        val response = restTemplate.getForEntity("/entity/99999", EntityResponse::class.java)

        assertEquals(404, response.statusCode.value())
    }

    @Test
    fun findById() {
        val response = restTemplate.getForEntity("/entity/10002", EntityResponse::class.java)

        assertEquals(200, response.statusCode.value())

        val documentContext: DocumentContext = JsonPath.parse(response.body)

        val entityResponse = documentContext.json<EntityResponse>()

        assertEquals(10002, entityResponse.id)
        assertEquals(10001, entityResponse.entityTypeResponse.id)
        assertEquals("entity_type_1", entityResponse.entityTypeResponse.name)
    }

    @Test
    fun create() {
        val request = EntityAddRequest()
        request.entityTypeId = 10001

        val uri = restTemplate.postForLocation("/entity", request, Any::class.java)
        assertTrue(uri.toString().matches(Regex(".*/entity/[0-9]+")))
    }

    @Test
    fun update() {
        val request = EntityUpdateRequest()
        request.id = 10001
        request.name = "new_name_updated"
        request.description = "new_description"
        restTemplate.put("/entity", request, EntityResponse::class.java)
        val response = restTemplate.getForEntity("/entity/10001", EntityResponse::class.java)
        assertEquals(200, response.statusCode.value())

        val documentContext: DocumentContext = JsonPath.parse(response.body)
        val entityResponse = documentContext.json<EntityResponse>()
        assertEquals(10001, entityResponse.id)
    }

    @Test
    fun deleteById() {
        assertEquals(200, restTemplate.getForEntity("/entity/10002", EntityResponse::class.java).statusCode.value())
        restTemplate.delete("/entity/10002")
        assertEquals(404, restTemplate.getForEntity("/entity/10002", EntityResponse::class.java).statusCode.value())
    }
}