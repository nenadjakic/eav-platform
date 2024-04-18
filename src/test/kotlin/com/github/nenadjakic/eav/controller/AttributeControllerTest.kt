package com.github.nenadjakic.eav.controller

import com.github.nenadjakic.eav.dto.AttributeResponse
import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.TestPropertySource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations=["classpath:application.properties"])
class AttributeControllerTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun findAll() {
        val response = restTemplate.getForEntity("/attribute", List::class.java)

        assertEquals(200, response.statusCode.value())
        assertTrue(response.body.size > 10)
    }

    @Test
    fun findPage() {
        val response = restTemplate.getForEntity("/attribute/page?pageNumber={pageNumber}&pageSize={pageSize}", Any::class.java, 0, 10)
        assertEquals(200, response.statusCode.value())
        assertEquals(10, (response.body as Map<*, *>)["size"])
        assertEquals(10, ((response.body as Map<*, *>)["content"] as Collection<*>).size)
    }

    @Test
    fun findById() {
        val response = restTemplate.getForEntity("/attribute/10002", AttributeResponse::class.java)

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