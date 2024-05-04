package com.github.nenadjakic.eav.repository

import com.github.nenadjakic.eav.entity.EntityType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface EntityTypeRepository : JpaRepository<EntityType, Long> {

    @Query(value = "select case when count(et) > 0 then true else false end from EntityType et " +
            "join Entity e on et.id = e.entityType.id " +
            "join Attribute a on et.id = a.entityType.id " +
            "where e.id = :entityId and a.id = :attributeId")
    fun existsByEntityIdAndAttributeId(@Param("entityId") entityId: Long, @Param("attributeId") attributeId: Long): Boolean
}