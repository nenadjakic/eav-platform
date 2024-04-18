package com.github.nenadjakic.eav.controller

import com.github.nenadjakic.eav.extension.collectionMap
import com.github.nenadjakic.eav.dto.EntityAddRequest
import com.github.nenadjakic.eav.dto.EntityResponse
import com.github.nenadjakic.eav.dto.EntityUpdateRequest
import com.github.nenadjakic.eav.entity.Entity
import com.github.nenadjakic.eav.service.EntityService
import com.github.nenadjakic.eav.service.model.Pager
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

@Tag(name = "Entity controller", description = "API endpoints for managing entities.")
@RestController
@RequestMapping("/entity")
class EntityController(
    private val modelMapper: ModelMapper,
    private val entityService: EntityService
): EavReadController<EntityResponse>, EavWriteController<EntityAddRequest, EntityUpdateRequest> {

    @Operation(
        operationId = "findAllEntites",
        summary = "Get all entities",
        description = "Returns a list of all entities.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful operation")
        ]
    )
    open override fun findAll(): ResponseEntity<List<EntityResponse>> {
        val entities = entityService.findAll()
        val response = modelMapper.collectionMap(entities, EntityResponse::class.java)

        return ResponseEntity.ok(response)
    }

    @Operation(
        operationId = "findPageWithEntities",
        summary = "Get entities by page.",
        description = "Returns a page of entities based on page number and page size.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved page of entities.")
        ]
    )
    open override fun findPage(pageNumber: Int, pageSize: Int?): ResponseEntity<Page<EntityResponse>> {
        val pagerBuilder = Pager.Builder().withPageNumber(pageNumber)
        if (pageSize != null) {
            pagerBuilder.withPageSize(pageSize)
        }
        val page = entityService.findPage(pagerBuilder.build())
        val response = page.map { modelMapper.map(it, EntityResponse::class.java) }

        return ResponseEntity.ok(response)
    }

    @Operation(
        operationId = "findEntityById",
        summary = "Get entity by id.",
        description = "Returns an entity with the specified id."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved entity."),
            ApiResponse(responseCode = "404", description = "Entity not found.")
        ]
    )
    open override fun findById(id: Long): ResponseEntity<EntityResponse> {
        val entity = entityService.findById(id)
        val response = entity?.let { modelMapper.map(entity, EntityResponse::class.java) }

        return ResponseEntity.ofNullable(response)
    }

    @Operation(
        operationId = "createEntity",
        summary = "Create entity.",
        description = "Creates a new entity based on the provided model."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Entity created successfully."),
            ApiResponse(responseCode = "400", description = "Invalid request data.")
        ]
    )
    open override fun create(model: EntityAddRequest): ResponseEntity<Void> {
        val entity = modelMapper.map(model, Entity::class.java)
        val createdEntity = entityService.create(entity)
        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdEntity.id)
            .toUri()

        return ResponseEntity.created(location).build()
    }

    @Operation(
        operationId = "updateEntity",
        summary = "Update entity.",
        description = "Updates an existing entity based on the provided model."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Entity updated successfully."),
            ApiResponse(responseCode = "400", description = "Invalid request data.")
        ]
    )
    open override fun update(model: EntityUpdateRequest): ResponseEntity<Void> {
        val entity = modelMapper.map(model, Entity::class.java)
        entityService.update(entity)
        return ResponseEntity.noContent().build()
    }

    @Operation(
        operationId = "deleteEntityById",
        summary = "Delete entity by id.",
        description = "Deletes an entity with the specified id."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Entity deleted successfully")
        ]
    )
    open override fun deleteById(id: Long): ResponseEntity<Void> {
        entityService.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}