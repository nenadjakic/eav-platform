package com.github.nenadjakic.eav.repository

import com.github.nenadjakic.eav.entity.EntityType
import org.springframework.data.jpa.repository.JpaRepository

interface EntityTypeRepository : JpaRepository<EntityType, Long>