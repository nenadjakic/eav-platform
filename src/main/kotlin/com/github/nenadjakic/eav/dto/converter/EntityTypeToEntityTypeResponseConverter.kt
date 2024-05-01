package com.github.nenadjakic.eav.dto.converter

import com.github.nenadjakic.eav.dto.AttributeResponse
import com.github.nenadjakic.eav.dto.EntityTypeResponse
import com.github.nenadjakic.eav.dto.MetadataResponse
import com.github.nenadjakic.eav.entity.EntityType
import org.modelmapper.AbstractConverter

class EntityTypeToEntityTypeResponseConverter : AbstractConverter<EntityType, EntityTypeResponse>() {
    override fun convert(source: EntityType?): EntityTypeResponse {
        val attributeResponses: MutableList<AttributeResponse> = mutableListOf()
        source!!.attributes.forEach { attributeResponses.add(AttributeResponse(
            it.id!!,
            it.code,
            it.name,
            it.description,
            MetadataResponse(
                it.metadata.id!!,
                it.metadata.dataType,
                it.metadata.required,
                it.metadata.repeatable,
                it.metadata.minValue,
                it.metadata.maxValue,
                it.metadata.minLength,
                it.metadata.maxLength
            )
        )) }
        return EntityTypeResponse(
            source.id!!,
            source.code,
            source.name,
            source.description,
            attributeResponses
        )
    }
}