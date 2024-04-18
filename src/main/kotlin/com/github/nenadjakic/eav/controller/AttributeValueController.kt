package com.github.nenadjakic.eav.controller

import com.github.nenadjakic.eav.dto.AttributeValueResponse
import com.github.nenadjakic.eav.extension.collectionMap
import com.github.nenadjakic.eav.service.AttributeValueService
import io.swagger.v3.oas.annotations.tags.Tag
import org.modelmapper.ModelMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Attribute-value controller", description = "API endpoints for managing values of attributes.")
@RestController
@RequestMapping("/attribute-value")
open class AttributeValueController(
    val modelMapper: ModelMapper,
    val attributeValueService: AttributeValueService
) {

    @GetMapping("entity/{entityId}")
    open fun findByEntityId(@PathVariable entityId: Long): ResponseEntity<List<AttributeValueResponse>> {
        val attributeValues = attributeValueService.findByEntityId(entityId)
        val response = modelMapper.collectionMap(attributeValues, AttributeValueResponse::class.java)
        return ResponseEntity.ok(response)
    }

}