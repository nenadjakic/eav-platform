package com.github.nenadjakic.eav.controller

import com.github.nenadjakic.eav.dto.EntityRequest
import com.github.nenadjakic.eav.dto.EntityResponse
import com.github.nenadjakic.eav.entity.Entity
import com.github.nenadjakic.eav.service.EntityService
import com.github.nenadjakic.eav.service.model.Pager
import org.modelmapper.ModelMapper
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/entity")
class EntityController(
    private val modelMapper: ModelMapper,
    private val entityService: EntityService
): EavController<EntityRequest, EntityResponse> {


    open override fun findAll(): ResponseEntity<List<EntityResponse>> {
        val entities = entityService.findAll()
        val response: MutableList<EntityResponse> = mutableListOf()
        entities.forEach { response.add(modelMapper.map(it, EntityResponse::class.java)) }

        return ResponseEntity.ok(response)
    }

    open override fun findPage(pageNumber: Int, pageSize: Int?): ResponseEntity<Page<EntityResponse>> {
        val pagerBuilder = Pager.Builder().withPageNumber(pageNumber)
        if (pageSize != null) {
            pagerBuilder.withPageSize(pageSize)
        }
        val page = entityService.findPage(pagerBuilder.build())
        val response = page.map { modelMapper.map(it, EntityResponse::class.java) }

        return ResponseEntity.ok(response)
    }

    open override fun findById(id: Long): ResponseEntity<EntityResponse> {
        val entity = entityService.findById(id)
        val response:EntityResponse? = entity?.let { modelMapper.map(entity, EntityResponse::class.java) }

        return ResponseEntity.ofNullable(response)
    }

    open override fun create(model: EntityRequest): ResponseEntity<Void> {
        model.id = null;
        val entity = modelMapper.map(model, Entity::class.java)
        val createdEntity = entityService.create(entity)
        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdEntity.id)
            .toUri()

        return ResponseEntity.created(location).build()
    }

    open override fun update(model: EntityRequest): ResponseEntity<Void> {
        val entity = modelMapper.map(model, Entity::class.java)
        entityService.update(entity)
        return ResponseEntity.ok().build()
    }

    open override fun deleteById(id: Long): ResponseEntity<Void> {
        entityService.deleteById(id)
        return ResponseEntity.ok().build()
    }
}