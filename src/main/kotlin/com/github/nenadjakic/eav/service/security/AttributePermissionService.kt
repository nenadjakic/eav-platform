package com.github.nenadjakic.eav.service.security

import com.github.nenadjakic.eav.entity.security.AttributePermission
import com.github.nenadjakic.eav.entity.security.RoleAttributeId
import com.github.nenadjakic.eav.repository.security.AttributePermissionRepository
import org.springframework.cache.annotation.Cacheable

import org.springframework.stereotype.Service

@Service
class AttributePermissionService(
    private val attributePermissionRepository: AttributePermissionRepository
) {

    fun findAll(): List<AttributePermission> = attributePermissionRepository.findAll()

    fun findAllForRoleName(roleName: String): List<AttributePermission> = attributePermissionRepository.findAllByRoleName(roleName)

    @Cacheable(value = ["attributePermissions"], key = "{ #root.methodName, #roleId, #attributeId }")
    fun findById(roleId: Long, attributeId: Long): AttributePermission? {
        val roleAttributeId = RoleAttributeId()
        roleAttributeId.roleId = roleId
        roleAttributeId.attributeId = attributeId

        return attributePermissionRepository.findById(roleAttributeId).orElse(null)
    }

    @Cacheable(value = ["attributePermissions"], key = "{ #roleName, #attributeId }")
    fun findByRoleNameAndAttributeId(roleName: String, attributeId: Long): AttributePermission? {
             return attributePermissionRepository.findByRoleNameAndAttributeId(roleName, attributeId)
    }
}