package com.github.nenadjakic.eav.service

import com.github.nenadjakic.eav.entity.Attribute
import com.github.nenadjakic.eav.repository.AttributeRepository
import com.github.nenadjakic.eav.service.model.Pager
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
open class AttributeService(private val attributeRepository: AttributeRepository): EavService<Attribute> {
    open override fun findById(id: Long): Attribute? = attributeRepository.findById(id).orElse(null)

    open override fun findAll(): List<Attribute> = attributeRepository.findAll()

    open override fun findPage(pager: Pager): Page<Attribute> = attributeRepository.findAll(pager.toPageRequest())

    open override fun deleteById(id: Long) = attributeRepository.deleteById(id)

    open override fun delete(entity: Attribute) = attributeRepository.delete(entity)

    open override fun update(entity: Attribute): Attribute = attributeRepository.save(entity)

    open override fun create(entity: Attribute): Attribute = attributeRepository.save(entity)
}