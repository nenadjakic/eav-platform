package com.github.nenadjakic.eav.service

import com.github.nenadjakic.eav.entity.AttributeValue
import com.github.nenadjakic.eav.entity.EntityAttributeId
import com.github.nenadjakic.eav.repository.AttributeValueRepository
import com.github.nenadjakic.eav.service.model.Pager
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
open class AttributeValueService(
    val attributeValueRepository: AttributeValueRepository
) {
    open fun findById(entityId: Long, attributeId: Long): AttributeValue? = attributeValueRepository.findById(getEntityAttributeId(entityId, attributeId)).orElse(null)

    open  fun findByEntityId(entityId: Long) : List<AttributeValue> = attributeValueRepository.findByEntityId(entityId)

    open fun findAll(): List<AttributeValue> {
        TODO("Not yet implemented")
    }

    open fun findPage(pager: Pager): Page<AttributeValue> {
        TODO("Not yet implemented")
    }

    open fun deleteById(entityId: Long, attributeId: Long) = attributeValueRepository.deleteById(getEntityAttributeId(entityId, attributeId))

    open fun delete(entity: AttributeValue) = attributeValueRepository.delete(entity)

    open fun update(entity: AttributeValue): AttributeValue {
        return attributeValueRepository.save(entity)
    }

    open fun create(entity: AttributeValue): AttributeValue {
        return attributeValueRepository.save(entity)
    }

    private fun getEntityAttributeId(entityId: Long, attributeId: Long): EntityAttributeId {
        val entityAttributeId = EntityAttributeId()
        entityAttributeId.entityId = entityId
        entityAttributeId.attributeId = attributeId

        return entityAttributeId
    }
}