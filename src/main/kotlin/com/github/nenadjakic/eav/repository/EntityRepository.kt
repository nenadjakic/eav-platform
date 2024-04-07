package com.github.nenadjakic.eav.repository

import com.github.nenadjakic.eav.entity.Entity
import org.springframework.data.jpa.repository.JpaRepository

interface EntityRepository : JpaRepository<Entity, Long>