package com.github.nenadjakic.eav.dto.converter

import com.github.nenadjakic.eav.dto.AttributeValueAddRequest
import com.github.nenadjakic.eav.entity.Attribute
import com.github.nenadjakic.eav.entity.AttributeValue
import com.github.nenadjakic.eav.entity.Entity
import com.github.nenadjakic.eav.entity.EntityAttributeId
import org.modelmapper.AbstractConverter

class AttributeValueAddRequestToAttributeValueConverter : AbstractConverter<AttributeValueAddRequest, AttributeValue>() {
    override fun convert(source: AttributeValueAddRequest?): AttributeValue {
        val destination = AttributeValue()
        destination.entityAttributeId = EntityAttributeId()
        destination.entity = Entity()
        destination.entity.id = source!!.entityId!!
        destination.attribute = Attribute()
        destination.attribute.id = source.attributeId!!
        destination.value = source.value
        return destination
    }
}