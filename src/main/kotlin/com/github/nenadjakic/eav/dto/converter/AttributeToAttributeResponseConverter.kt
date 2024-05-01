package com.github.nenadjakic.eav.dto.converter

import com.github.nenadjakic.eav.dto.AttributeResponse
import com.github.nenadjakic.eav.dto.MetadataResponse
import com.github.nenadjakic.eav.entity.Attribute
import org.modelmapper.AbstractConverter

class AttributeToAttributeResponseConverter : AbstractConverter<Attribute, AttributeResponse>() {
    override fun convert(source: Attribute?): AttributeResponse {
        val metadata = source!!.metadata
        val metadataResponse: MetadataResponse = MetadataResponse(
            metadata.id!!,
            metadata.dataType,
            metadata.required,
            metadata.repeatable,
            metadata.minValue,
            metadata.maxValue,
            metadata.minLength,
            metadata.maxLength
        )
        return AttributeResponse(
            source.id!!,
            source.code,
            source.name,
            source.description,
            metadataResponse)
    }

}