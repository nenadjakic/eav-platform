package com.github.nenadjakic.eav.dto.converter

import com.github.nenadjakic.eav.dto.AttributeUpdateRequest
import com.github.nenadjakic.eav.entity.Attribute
import com.github.nenadjakic.eav.entity.Metadata
import org.modelmapper.AbstractConverter

class AttributeUpdateRequestToAttributeConverter : AbstractConverter<AttributeUpdateRequest, Attribute>() {
    override fun convert(source: AttributeUpdateRequest?): Attribute {
        val destination: Attribute = Attribute()
        destination.id = source!!.id
        destination.code = source.code
        destination.name = source.name
        destination.description = source.description

        val metadata = Metadata()
        destination.metadata = metadata
        metadata.id = source.id
        metadata.attribute = destination
        metadata.dataType = source.metadata.dataType
        metadata.required = source.metadata.required
        metadata.repeatable = source.metadata.repeatable
        if (source.metadata.minMaxValue != null) {
            metadata.minValue = source.metadata.minMaxValue!!.min
            metadata.minValue = source.metadata.minMaxValue!!.max
        }
        if (source.metadata.minMaxLength != null) {
            metadata.minLength = source.metadata.minMaxLength!!.min
            metadata.maxLength = source.metadata.minMaxLength!!.max
        }

        return destination
    }
}