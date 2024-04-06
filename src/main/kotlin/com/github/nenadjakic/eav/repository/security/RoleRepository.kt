package com.github.nenadjakic.eav.repository.security

import com.github.nenadjakic.eav.entity.security.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long>