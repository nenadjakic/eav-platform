package com.github.nenadjakic.eav.dto.converter

import com.github.nenadjakic.eav.dto.AttributeSimpleResponse
import com.github.nenadjakic.eav.dto.AttributeValueResponse
import com.github.nenadjakic.eav.dto.EntitySimpleResponse
import com.github.nenadjakic.eav.entity.AttributeValue
import org.modelmapper.AbstractConverter

class AttributeValueToAttributeValueResponseConverter : AbstractConverter<AttributeValue, AttributeValueResponse>() {
    override fun convert(source: AttributeValue?): AttributeValueResponse {

        return AttributeValueResponse(
            EntitySimpleResponse(
                source!!.entity.id!!,
                source.entity.code,
                source.entity.description
            ),
            AttributeSimpleResponse(
                source.attribute.id!!,
                source.attribute.code,
                source.attribute.name,
                source.attribute.description
            ),
            source.value!!.toString())
    }
}