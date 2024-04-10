package com.github.nenadjakic.eav.dto.converter

import com.github.nenadjakic.eav.dto.EntityAddRequest
import com.github.nenadjakic.eav.entity.Entity
import org.modelmapper.AbstractConverter

class EntityRequestToEntityConverter : AbstractConverter<EntityAddRequest, Entity>() {
    override fun convert(source: EntityAddRequest?): Entity {
        val destination = Entity()

        destination.id = null
        destination.name = source!!.name
        destination.description = source.description

        return destination
    }
}