package com.github.nenadjakic.eav.service

import com.github.nenadjakic.eav.entity.Entity
import com.github.nenadjakic.eav.repository.EntityRepository
import com.github.nenadjakic.eav.service.model.Pager
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
open class EntityService(
    private val entityRepository: EntityRepository
) : EavService<Entity> {
    open override fun findById(id: Long): Entity? {
        return entityRepository.findById(id).orElse(null)
    }

    open override fun findAll(): List<Entity> {
        return entityRepository.findAll()
    }

    open override fun findPage(pager: Pager): Page<Entity> {
        return entityRepository.findAll(pager.toPageRequest())
    }

    open override fun delete(entity: Entity) {
        entityRepository.delete(entity)
    }

    open override fun deleteById(id: Long) {
        entityRepository.deleteById(id)
    }

    open override fun update(entity: Entity): Entity {
        return entityRepository.save(entity)
    }

    open override fun create(entity: Entity): Entity {
        return entityRepository.save(entity)
    }
}