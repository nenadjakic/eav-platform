package com.github.nenadjakic.eav.dto.converter

import com.github.nenadjakic.eav.dto.EntityTypeResponse
import com.github.nenadjakic.eav.entity.EntityType
import org.modelmapper.AbstractConverter

class EntityTypeToEntityTypeResponseConverter : AbstractConverter<EntityType, EntityTypeResponse>() {
    override fun convert(source: EntityType?): EntityTypeResponse {
        return EntityTypeResponse(source!!.id!!, source.name, source.description)
    }
}