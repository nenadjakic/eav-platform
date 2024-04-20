package com.github.nenadjakic.eav.controller

import com.github.nenadjakic.eav.dto.AttributeValueAddRequest
import com.github.nenadjakic.eav.dto.AttributeValueResponse
import com.github.nenadjakic.eav.entity.AttributeValue
import com.github.nenadjakic.eav.extension.collectionMap
import com.github.nenadjakic.eav.service.AttributeValueService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.modelmapper.ModelMapper
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@Tag(name = "Attribute-value controller", description = "API endpoints for managing values of attributes.")
@RestController
@RequestMapping("/attribute-value")
@Validated
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

    @GetMapping("/{entityId}-{attributeId}")
    open fun findById(@PathVariable entityId: Long, @PathVariable attributeId: Long): ResponseEntity<AttributeValueResponse> {
        val attributeValue = attributeValueService.findById(entityId, attributeId)
        val response = attributeValue?.let { modelMapper.map(attributeValue, AttributeValueResponse::class.java) }

        return ResponseEntity.ofNullable(response)
    }

    @PostMapping
    open fun create(@RequestBody @Valid attributeValueAddRequest: AttributeValueAddRequest): ResponseEntity<Void> {
        val attributeValue = modelMapper.map(attributeValueAddRequest, AttributeValue::class.java)
        val createdAttributeValue = attributeValueService.create(attributeValue)

        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{entityId}-{attributeId}")
            .buildAndExpand(createdAttributeValue.entityAttributeId.entity.id, createdAttributeValue.entityAttributeId.attribute.id)
            .toUri()

        return ResponseEntity.created(location).build()
    }
}