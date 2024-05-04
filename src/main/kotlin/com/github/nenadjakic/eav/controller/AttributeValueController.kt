package com.github.nenadjakic.eav.controller

import com.github.nenadjakic.eav.dto.AttributeValueAddRequest
import com.github.nenadjakic.eav.dto.AttributeValueResponse
import com.github.nenadjakic.eav.entity.AttributeValue
import com.github.nenadjakic.eav.extension.collectionMap
import com.github.nenadjakic.eav.service.AttributeService
import com.github.nenadjakic.eav.service.AttributeValueService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.modelmapper.ModelMapper
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder


@Tag(name = "Attribute-value controller", description = "API endpoints for managing values of attributes.")
@RestController
@RequestMapping("/attribute-value")
@Validated
open class AttributeValueController(
    val modelMapper: ModelMapper,
    val attributeService: AttributeService,
    val attributeValueService: AttributeValueService
) {

    //@InitBinder(value = ["attributeValueAddRequest"])
    protected fun initBinder(binder: WebDataBinder) {
        // može i kao bean
        //binder.addValidators(AttributeValueValidator(attributeService))
    }

    @Operation(
        operationId = "findAttributeValueById",
        summary = "Get attribute-value by id.",
        description = "Returns an attribute-value with the specified id (entityId-attributeId)."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved attribute-value."),
            ApiResponse(responseCode = "404", description = "Attribute-value not found.")
        ]
    )
    @GetMapping("/{entityId}-{attributeId}")
    open fun findById(@PathVariable entityId: Long, @PathVariable attributeId: Long): ResponseEntity<AttributeValueResponse> {
        val attributeValue = attributeValueService.findById(entityId, attributeId)
        val response = attributeValue?.let { modelMapper.map(attributeValue, AttributeValueResponse::class.java) }

        return ResponseEntity.ofNullable(response)
    }

    @Operation(
        operationId = "findAttributeValueByEntityId",
        summary = "Get all attribute-value by entitiyId.",
        description = "Returns all attribute-values with the specified entityId."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved attribute-value."),
            ApiResponse(responseCode = "404", description = "Attribute-value not found.")
        ]
    )
    @GetMapping("entity/{entityId}")
    open fun findByEntityId(@PathVariable entityId: Long): ResponseEntity<List<AttributeValueResponse>> {
        val attributeValues = attributeValueService.findByEntityId(entityId)
        val response = modelMapper.collectionMap(attributeValues, AttributeValueResponse::class.java)
        return ResponseEntity.ok(response)
    }

    @Operation(
        operationId = "createAttributeValue",
        summary = "Create attribute-vale.",
        description = "Creates a new attribute-value based on the provided model."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Attribute-value created successfully."),
            ApiResponse(responseCode = "400", description = "Invalid request data.")
        ]
    )
    @PostMapping
    @PreAuthorize("@attributeSecurityService.canCreate(#attributeValueAddRequest.attributeId)")
    open fun create(@RequestBody @Valid attributeValueAddRequest: AttributeValueAddRequest): ResponseEntity<Void> {
        val attributeValue = modelMapper.map(attributeValueAddRequest, AttributeValue::class.java)
        val createdAttributeValue = attributeValueService.create(attributeValue)

        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{entityId}-{attributeId}")
            .buildAndExpand(createdAttributeValue.entity.id, createdAttributeValue.attribute.id)
            .toUri()

        return ResponseEntity.created(location).build()
    }

    @Operation(
        operationId = "updateAttributeValue",
        summary = "Update attribute-value.",
        description = "Updates an existing attribute-value based on the provided model."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Attribute-value updated successfully."),
            ApiResponse(responseCode = "400", description = "Invalid request data.")
        ]
    )
    @PutMapping
    @PreAuthorize("@attributeSecurityService.canUpdate(#attributeValueAddRequest.attributeId)")
    open fun update(@RequestBody @Valid attributeValueAddRequest: AttributeValueAddRequest): ResponseEntity<Void> {
        val attributeValue = modelMapper.map(attributeValueAddRequest, AttributeValue::class.java)
        attributeValueService.update(attributeValue)
        return ResponseEntity.noContent().build()
    }

    @Operation(
        operationId = "deleteAttributeValueById",
        summary = "Delete attribute-value by id.",
        description = "Deletes an attribute-value with the specified id."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Attribute-value deleted successfully")
        ]
    )
    @DeleteMapping
    @PreAuthorize("@attributeSecurityService.canDelete(#attributeId)")
    open fun deleteById(@PathVariable entityId: Long, @PathVariable attributeId: Long): ResponseEntity<Void> {
        attributeValueService.deleteById(entityId, attributeId)
        return ResponseEntity.noContent().build()
    }
}