package com.github.nenadjakic.eav.security

import com.github.nenadjakic.eav.entity.security.Action
import com.github.nenadjakic.eav.entity.security.AttributePermission
import com.github.nenadjakic.eav.security.model.SecurityUser
import com.github.nenadjakic.eav.service.security.AttributePermissionService
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class AttributeSecurityService(
    private val attributePermissionService: AttributePermissionService
) {
    fun canRead(root: MethodSecurityExpressionOperations, attributeId: Long): Boolean {
        val user = (root.authentication.principal as SecurityUser?) ?: throw Exception("User does not exists.")
        val roles = getRoles(user)
        for (role in roles) {
            val attributePermission = attributePermissionService.findByRoleNameAndAttributeId(role, attributeId)
            if (attributePermission != null) {
                if (attributePermission.actions.contains(Action.READ)) {
                    return true
                }
            }
        }
        return false
    }

    fun canRead(attributeId: Long): Boolean = hasPermission(attributeId, Action.READ)

    fun canCreate(attributeId: Long): Boolean = hasPermission(attributeId, Action.CREATE)

    fun canUpdate(attributeId: Long): Boolean = hasPermission(attributeId, Action.UPDATE)

    fun canDelete(attributeId: Long): Boolean = hasPermission(attributeId, Action.DELETE)

    private fun hasPermission(attributeId: Long, action: Action): Boolean {
        var hasPermission = false;
        for (role in getRoles(getUser() ?: throw Exception("User does not exists."))) {
            hasPermission = hasPermission(role, attributeId, action)
            if (hasPermission) {
                break
            }
        }
        return hasPermission
    }

    private fun hasPermission(role: String, attributeId: Long, action: Action): Boolean = hasPermission(attributePermissionService.findByRoleNameAndAttributeId(role, attributeId), action)

    private fun hasPermission(attributePermission: AttributePermission?, action: Action): Boolean {
        return attributePermission != null && attributePermission.actions.contains(action)
    }

    private fun getUser(): SecurityUser? {
        return if (SecurityContextHolder.getContext().authentication == null) {
            null
        } else {
            (SecurityContextHolder.getContext().authentication.principal as SecurityUser?)
        }
    }

    private fun getRoles(user: UserDetails): List<out String> = user.authorities.map { it.authority }
}