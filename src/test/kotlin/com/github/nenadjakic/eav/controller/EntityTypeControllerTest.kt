package com.github.nenadjakic.eav.controller

import com.github.nenadjakic.eav.dto.*
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
import org.springframework.test.context.TestPropertySource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations=["classpath:application.properties"])
class EntityTypeControllerTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("Get all entity types successfully.")
    fun findAll() {
        val response = restTemplate.getForEntity("/entity-type", List::class.java)

        assertEquals(200, response.statusCode.value())
        assertTrue(response.body.size > 10)
    }

    @Test
    @DisplayName("Get first page successfully.")
    fun findPage() {
        val response = restTemplate.getForEntity("/entity-type/page?pageNumber={pageNumber}&pageSize={pageSize}", Any::class.java, 0, 10)
        assertEquals(200, response.statusCode.value())
        assertEquals(10, (response.body as Map<*, *>)["size"])
        assertEquals(10, ((response.body as Map<*, *>)["content"] as Collection<*>).size)
    }

    @Test
    @DisplayName("Get by id where id does not exist.")
    fun findById_NotFound() {
        val response = restTemplate.getForEntity("/entity-type/99999", EntityTypeResponse::class.java)

        assertEquals(404, response.statusCode.value())
    }

    @Test
    @DisplayName("Get by id successfully.")
    fun findById() {
        val response = restTemplate.getForEntity("/entity-type/10002", EntityTypeResponse::class.java)

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
        val request = EntityTypeAddRequest()
        request.name = "new_name_added"
        request.description = "new_description"
        val uri = restTemplate.postForLocation("/entity-type", request, Any::class.java)
        val response = restTemplate.getForEntity(uri, EntityTypeResponse::class.java)
        assertEquals(200, response.statusCode.value())
    }

    @Test
    @DisplayName("Update existing entity type.")
    fun update() {
        val request = EntityTypeUpdateRequest()
        request.id = 10001
        request.name = "new_name_updated"
        request.description = "new_description"
        restTemplate.put("/entity-type", request, EntityTypeUpdateRequest::class.java)
        val response = restTemplate.getForEntity("/entity-type/10001", EntityTypeResponse::class.java)
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
        assertEquals(200, restTemplate.getForEntity("/entity-type/10002", EntityTypeResponse::class.java).statusCode.value())
        restTemplate.delete("/entity-type/10002")
        assertEquals(404, restTemplate.getForEntity("/entity-type/10002", EntityTypeResponse::class.java).statusCode.value())
    }
}