package com.github.nenadjakic.eav.repository

import com.github.nenadjakic.eav.entity.Entity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface EntityRepository : JpaRepository<Entity, Long> {
    @EntityGraph(attributePaths = ["entityType"])
    override fun findAll(): List<Entity>

    @EntityGraph(attributePaths = ["entityType"])
    override fun findAll(pageable: Pageable): Page<Entity>
}