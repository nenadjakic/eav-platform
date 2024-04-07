package com.github.nenadjakic.eav.dto.converter

import com.github.nenadjakic.eav.dto.EntityResponse
import com.github.nenadjakic.eav.entity.Entity
import org.modelmapper.AbstractConverter

class EntityToEntityResponseConverter : AbstractConverter<Entity, EntityResponse>() {
    override fun convert(source: Entity?): EntityResponse {
        return EntityResponse(
            source!!.id,
            source.name,
            source.description
        )
    }

}