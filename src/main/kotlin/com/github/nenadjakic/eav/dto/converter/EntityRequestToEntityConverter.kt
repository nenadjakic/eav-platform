package com.github.nenadjakic.eav.dto.converter

import com.github.nenadjakic.eav.dto.EntityRequest
import com.github.nenadjakic.eav.entity.Entity
import org.modelmapper.AbstractConverter

class EntityRequestToEntityConverter : AbstractConverter<EntityRequest, Entity>() {
    override fun convert(source: EntityRequest?): Entity {
        val destination = Entity()

        destination.id = source!!.id
        destination.name = source.name;
        destination.description = source.description

        return destination
    }
}