package com.github.nenadjakic.eav.dto.converter

import com.github.nenadjakic.eav.dto.AttributeSimpleResponse
import com.github.nenadjakic.eav.dto.AttributeValueResponse
import com.github.nenadjakic.eav.dto.EntitySimpleResponse
import com.github.nenadjakic.eav.entity.AttributeValue
import org.modelmapper.AbstractConverter

class AttributeValueToAttributeValueResponseConverter : AbstractConverter<AttributeValue, AttributeValueResponse>() {
    override fun convert(source: AttributeValue?): AttributeValueResponse {

        return AttributeValueResponse(
            EntitySimpleResponse(source!!.entityAttributeId.entity.id!!),
            AttributeSimpleResponse(
                source.entityAttributeId.attribute.id!!,
                source.entityAttributeId.attribute.name
            ),
            source.value!!.toString())
    }
}