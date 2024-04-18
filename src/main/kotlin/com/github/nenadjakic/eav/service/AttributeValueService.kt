package com.github.nenadjakic.eav.service

import com.github.nenadjakic.eav.entity.AttributeValue
import com.github.nenadjakic.eav.repository.AttributeValueRepository
import com.github.nenadjakic.eav.service.model.Pager
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
open class AttributeValueService(
    val attributeValueRepository: AttributeValueRepository
) : EavService<AttributeValue> {
    open override fun findById(id: Long): AttributeValue? = attributeValueRepository.findById(id).orElse(null)

    open  fun findByEntityId(entityId: Long) : List<AttributeValue> = attributeValueRepository.findByEntityAttributeId_Entity_Id(entityId)

    open override fun findAll(): List<AttributeValue> {
        TODO("Not yet implemented")
    }

    open override fun findPage(pager: Pager): Page<AttributeValue> {
        TODO("Not yet implemented")
    }

    open override fun deleteById(id: Long) {
        TODO("Not yet implemented")
    }

    open override fun delete(entity: AttributeValue) {
        TODO("Not yet implemented")
    }

    open override fun update(entity: AttributeValue): AttributeValue {
        TODO("Not yet implemented")
    }

    open override fun create(entity: AttributeValue): AttributeValue {
        TODO("Not yet implemented")
    }

}