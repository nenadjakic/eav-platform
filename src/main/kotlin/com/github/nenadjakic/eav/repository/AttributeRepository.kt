package com.github.nenadjakic.eav.repository

import com.github.nenadjakic.eav.entity.Attribute
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface AttributeRepository : JpaRepository<Attribute, Long> {

    @EntityGraph(attributePaths = [ "entityType", "metadata" ])
    override fun findAll(): List<Attribute>

    @EntityGraph(attributePaths = [ "entityType", "metadata" ])
    override fun findAll(pageable: Pageable): Page<Attribute>
}