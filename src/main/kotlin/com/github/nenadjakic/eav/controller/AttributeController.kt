package com.github.nenadjakic.eav.controller

import collectionMap
import com.github.nenadjakic.eav.util.RestUtil
import com.github.nenadjakic.eav.dto.AttributeAddResponse
import com.github.nenadjakic.eav.dto.AttributeAddRequest
import com.github.nenadjakic.eav.dto.AttributeUpdateRequest
import com.github.nenadjakic.eav.entity.Attribute
import com.github.nenadjakic.eav.service.AttributeService
import org.modelmapper.ModelMapper
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/attribute")
open class AttributeController(
    private val modelMapper: ModelMapper,
    private val attributeService: AttributeService
) : EavReadController<AttributeAddResponse>, EavWriteController<AttributeAddRequest, AttributeUpdateRequest> {

    open override fun findAll(): ResponseEntity<List<AttributeAddResponse>> {
        val attributes = attributeService.findAll()
        val response = modelMapper.collectionMap(attributes, AttributeAddResponse::class.java)
        return ResponseEntity.ok(response)
    }

    open override fun findPage(pageNumber: Int, pageSize: Int?): ResponseEntity<Page<AttributeAddResponse>> {
        val page = attributeService.findPage(RestUtil.getPager(pageNumber, pageSize))
        val response = page.map { modelMapper.map(it, AttributeAddResponse::class.java) }
        return ResponseEntity.ok(response)
    }

    open override fun findById(id: Long): ResponseEntity<AttributeAddResponse> {
        val attribute = attributeService.findById(id)
        val response: AttributeAddResponse? = attribute?.let { modelMapper.map(attribute, AttributeAddResponse::class.java) }
        return ResponseEntity.ofNullable(response)

    }

    open override fun deleteById(id: Long): ResponseEntity<Void> {
        attributeService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    open override fun update(model: AttributeUpdateRequest): ResponseEntity<Void> {
        val entity = modelMapper.map(model, Attribute::class.java)
        attributeService.update(entity)
        return ResponseEntity.noContent().build()
    }

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

