package com.github.nenadjakic.eav.service

import com.github.nenadjakic.eav.entity.EntityType
import com.github.nenadjakic.eav.repository.EntityTypeRepository
import com.github.nenadjakic.eav.service.model.Pager
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
open class EntityTypeService(
    val entityTypeRepository: EntityTypeRepository
) : EavService<EntityType> {
    open override fun findById(id: Long): EntityType? = entityTypeRepository.findById(id).orElse(null)

    open override fun findAll(): List<EntityType> = entityTypeRepository.findAll()

    open override fun findPage(pager: Pager): Page<EntityType> = entityTypeRepository.findAll(pager.toPageRequest())

    open override fun deleteById(id: Long) = entityTypeRepository.deleteById(id)

    open override fun delete(entity: EntityType) = entityTypeRepository.delete(entity)

    open override fun update(entity: EntityType): EntityType = entityTypeRepository.save(entity)

    open override fun create(entity: EntityType): EntityType = entityTypeRepository.save(entity)

    fun existsByEntityIdAndAttributeId(entityId: Long, attributeId: Long): Boolean = entityTypeRepository.existsByEntityIdAndAttributeId(entityId, attributeId)
}