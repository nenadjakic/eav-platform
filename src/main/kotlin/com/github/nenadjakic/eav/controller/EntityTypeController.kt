package com.github.nenadjakic.eav.controller

import com.github.nenadjakic.eav.dto.EntityTypeAddRequest
import com.github.nenadjakic.eav.dto.EntityTypeResponse
import com.github.nenadjakic.eav.dto.EntityTypeUpdateRequest
import com.github.nenadjakic.eav.entity.EntityType
import com.github.nenadjakic.eav.extension.collectionMap
import com.github.nenadjakic.eav.service.EntityTypeService
import com.github.nenadjakic.eav.util.RestUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.modelmapper.ModelMapper
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@Tag(name = "Entity type controller", description = "API endpoints for managing entity types.")
@RestController
@RequestMapping("/entity-type")
open class EntityTypeController(
    val modelMapper: ModelMapper,
    val entityTypeService: EntityTypeService
) : EavReadController<EntityTypeResponse>, EavWriteController<EntityTypeAddRequest, EntityTypeUpdateRequest> {

    @Operation(
        operationId = "findAllEntityTypes",
        summary = "Get all entity types",
        description = "Returns a list of all entity types.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful operation")
        ]
    )
    open override fun findAll(): ResponseEntity<List<EntityTypeResponse>> {
        val entityTypes = entityTypeService.findAll()
        val response = modelMapper.collectionMap(entityTypes, EntityTypeResponse::class.java)
        return ResponseEntity.ok(response)
    }

    @Operation(
        operationId = "findPageWithEntityTypes",
        summary = "Get entity types by page.",
        description = "Returns a page of entity types based on page number and page size.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved page of entity types.")
        ]
    )
    open override fun findPage(pageNumber: Int, pageSize: Int?): ResponseEntity<Page<EntityTypeResponse>> {
        val page = entityTypeService.findPage(RestUtil.getPager(pageNumber, pageSize))
        val response = page.map { modelMapper.map(it, EntityTypeResponse::class.java) }
        return ResponseEntity.ok(response)
    }

    @Operation(
        operationId = "findEntityTypeById",
        summary = "Get entity type by id.",
        description = "Returns an entity type with the specified id."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved entity type."),
            ApiResponse(responseCode = "404", description = "Entity type not found.")
        ]
    )
    open override fun findById(id: Long): ResponseEntity<EntityTypeResponse> {
        val entityType = entityTypeService.findById(id)
        val response = if (entityType != null) modelMapper.map(entityType, EntityTypeResponse::class.java) else null
        return ResponseEntity.ofNullable(response)
    }

    @Operation(
        operationId = "createEntityType",
        summary = "Create entity type.",
        description = "Creates a new entity type based on the provided model."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Entity type created successfully."),
            ApiResponse(responseCode = "400", description = "Invalid request data.")
        ]
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    open override fun create(model: EntityTypeAddRequest): ResponseEntity<Void> {
        val entity = modelMapper.map(model, EntityType::class.java)
        val createdEntity = entityTypeService.create(entity)
        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdEntity.id)
            .toUri()

        return ResponseEntity.created(location).build()
    }

    @Operation(
        operationId = "updateEntityType",
        summary = "Update entity type.",
        description = "Updates an existing entity type based on the provided model."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Entity type updated successfully."),
            ApiResponse(responseCode = "400", description = "Invalid request data.")
        ]
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    open override fun update(model: EntityTypeUpdateRequest): ResponseEntity<Void> {
        val entityType = modelMapper.map(model, EntityType::class.java)
        entityTypeService.update(entityType)
        return ResponseEntity.noContent().build()
    }

    @Operation(
        operationId = "deleteEntityTypeById",
        summary = "Delete entity type by id.",
        description = "Deletes an entity type with the specified id."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Entity type deleted successfully")
        ]
    )
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    open override fun deleteById(id: Long): ResponseEntity<Void> {
        entityTypeService.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}