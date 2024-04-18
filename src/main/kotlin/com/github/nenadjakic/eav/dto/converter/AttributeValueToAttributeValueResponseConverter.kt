package com.github.nenadjakic.eav.dto.converter

import com.github.nenadjakic.eav.dto.AttributeValueResponse
import com.github.nenadjakic.eav.entity.AttributeValue
import org.modelmapper.AbstractConverter

class AttributeValueToAttributeValueResponseConverter : AbstractConverter<AttributeValue, AttributeValueResponse>() {
    override fun convert(source: AttributeValue?): AttributeValueResponse {
        return AttributeValueResponse(
            source!!.entityAttributeId.entity.id!!,
            "",
            source.entityAttributeId.attribute.id!!,
            source.entityAttributeId.attribute.name,
            source.value)
    }
}