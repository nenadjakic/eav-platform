package com.github.nenadjakic.eav.dto.converter

import com.github.nenadjakic.eav.dto.EntityResponse
import com.github.nenadjakic.eav.dto.EntityTypeSimpleResponse
import com.github.nenadjakic.eav.entity.Entity
import org.modelmapper.AbstractConverter

class EntityToEntityResponseConverter : AbstractConverter<Entity, EntityResponse>() {
    override fun convert(source: Entity?): EntityResponse {
        return EntityResponse(
            source!!.id!!,
            EntityTypeSimpleResponse(
                source.entityType.id!!,
                source.entityType.name,
                source.entityType.description
            )
        )
    }
}