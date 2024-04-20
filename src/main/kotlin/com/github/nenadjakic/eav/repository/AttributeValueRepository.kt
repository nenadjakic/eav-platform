package com.github.nenadjakic.eav.repository

import com.github.nenadjakic.eav.entity.AttributeValue
import com.github.nenadjakic.eav.entity.EntityAttributeId
import org.springframework.data.jpa.repository.JpaRepository

interface AttributeValueRepository : JpaRepository<AttributeValue, EntityAttributeId> {
    @Suppress("FunctionName")
    fun findByEntityAttributeId_Entity_Id(entityId: Long): List<AttributeValue>
}