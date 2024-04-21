package com.github.nenadjakic.eav.dto.converter

import com.github.nenadjakic.eav.dto.AttributeSimpleResponse
import com.github.nenadjakic.eav.dto.AttributeValueSimpleResponse
import com.github.nenadjakic.eav.dto.EntityResponse
import com.github.nenadjakic.eav.dto.EntityTypeSimpleResponse
import com.github.nenadjakic.eav.entity.Entity
import org.modelmapper.AbstractConverter

class EntityToEntityResponseConverter : AbstractConverter<Entity, EntityResponse>() {
    override fun convert(source: Entity?): EntityResponse {
        val attibuteValues: MutableList<AttributeValueSimpleResponse> = mutableListOf()
        source!!.attributeValues.forEach { attibuteValues.add(AttributeValueSimpleResponse(
            AttributeSimpleResponse(it.attribute.id!!, it.attribute.name),
            it.value
        )) }
        return EntityResponse(
            source.id!!,
            EntityTypeSimpleResponse(
                source.entityType.id!!,
                source.entityType.name,
                source.entityType.description
            ),
            attibuteValues
        )
    }
}