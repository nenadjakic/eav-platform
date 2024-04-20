package com.github.nenadjakic.eav.repository.security

import com.github.nenadjakic.eav.entity.security.AttributePermission
import com.github.nenadjakic.eav.entity.security.RoleAttributeId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AttributePermissionRepository : JpaRepository<AttributePermission, RoleAttributeId> {

    fun findAllByRoleName(roleName: String): List<AttributePermission>

    fun findByRoleNameAndAttributeId(roleName: String, attributeId: Long): AttributePermission
}