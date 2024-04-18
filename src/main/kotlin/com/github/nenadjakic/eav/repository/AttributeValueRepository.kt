package com.github.nenadjakic.eav.repository

import com.github.nenadjakic.eav.entity.AttributeValue
import org.springframework.data.jpa.repository.JpaRepository

interface AttributeValueRepository : JpaRepository<AttributeValue, Long> {
    fun findByEntityAttributeId_Entity_Id(entityId: Long): List<AttributeValue>
}