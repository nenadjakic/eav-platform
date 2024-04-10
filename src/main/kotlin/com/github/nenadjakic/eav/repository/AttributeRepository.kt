package com.github.nenadjakic.eav.repository

import com.github.nenadjakic.eav.entity.Attribute
import org.springframework.data.jpa.repository.JpaRepository

interface AttributeRepository : JpaRepository<Attribute, Long>