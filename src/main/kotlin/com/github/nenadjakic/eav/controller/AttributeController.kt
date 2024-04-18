package com.github.nenadjakic.eav.controller

import com.github.nenadjakic.eav.dto.AttributeAddRequest
import com.github.nenadjakic.eav.dto.AttributeResponse
import com.github.nenadjakic.eav.dto.AttributeUpdateRequest
import com.github.nenadjakic.eav.entity.Attribute
import com.github.nenadjakic.eav.extension.collectionMap
import com.github.nenadjakic.eav.service.AttributeService
import com.github.nenadjakic.eav.util.RestUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.modelmapper.ModelMapper
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@Tag(name = "Attribute controller", description = "API endpoints for managing attributes.")
@RestController
@RequestMapping("/attribute")
open class AttributeController(
    private val modelMapper: ModelMapper,
    private val attributeService: AttributeService
) : EavReadController<AttributeResponse>, EavWriteController<AttributeAddRequest, AttributeUpdateRequest> {

    @Operation(
        operationId = "findAllAttributes",
        summary = "Get all attributes",
        description = "Returns a list of all attributes.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful operation")
        ]
    )
    open override fun findAll(): ResponseEntity<List<AttributeResponse>> {
        val attributes = attributeService.findAll()
        val response = modelMapper.collectionMap(attributes, AttributeResponse::class.java)
        return ResponseEntity.ok(response)
    }

    @Operation(
        operationId = "findPageWithAttributes",
        summary = "Get attributes by page.",
        description = "Returns a page of attributes based on page number and page size.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved page of attributes.")
        ]
    )
    open override fun findPage(pageNumber: Int, pageSize: Int?): ResponseEntity<Page<AttributeResponse>> {
        val page = attributeService.findPage(RestUtil.getPager(pageNumber, pageSize))
        val response = page.map { modelMapper.map(it, AttributeResponse::class.java) }
        return ResponseEntity.ok(response)
    }

    @Operation(
        operationId = "findAttributeById",
        summary = "Get attribute by id.",
        description = "Returns an attribute with the specified id."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved attribute."),
            ApiResponse(responseCode = "404", description = "Attribute not found.")
        ]
    )
    open override fun findById(id: Long): ResponseEntity<AttributeResponse> {
        val attribute = attributeService.findById(id)
        val response: AttributeResponse? = attribute?.let { modelMapper.map(attribute, AttributeResponse::class.java) }
        return ResponseEntity.ofNullable(response)
    }

    @Operation(
        operationId = "deleteAttributeById",
        summary = "Delete attribute by id.",
        description = "Deletes an attribute with the specified id."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Entity deleted successfully")
        ]
    )
    open override fun deleteById(id: Long): ResponseEntity<Void> {
        attributeService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @Operation(
        operationId = "updateAttribute",
        summary = "Update attribute.",
        description = "Updates an existing attribute based on the provided model."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Attribute updated successfully."),
            ApiResponse(responseCode = "400", description = "Invalid request data.")
        ]
    )
    open override fun update(model: AttributeUpdateRequest): ResponseEntity<Void> {
        val entity = modelMapper.map(model, Attribute::class.java)
        attributeService.update(entity)
        return ResponseEntity.noContent().build()
    }

    @Operation(
        operationId = "createAttribute",
        summary = "Create attribute.",
        description = "Creates a new attribute based on the provided model."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Attribute created successfully."),
            ApiResponse(responseCode = "400", description = "Invalid request data.")
        ]
    )
    open override fun create(model: AttributeAddRequest): ResponseEntity<Void> {
        val entity = modelMapper.map(model, Attribute::class.java)
        val createdEntity = attributeService.create(entity)
        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdEntity.id)
            .toUri()

        return ResponseEntity.created(location).build()
    }
}

