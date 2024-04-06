package com.github.nenadjakic.eav.security

import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import java.io.Serializable


class AttributePermissionEvaluator : PermissionEvaluator {
    override fun hasPermission(authentication: Authentication?, targetDomainObject: Any?, permission: Any?): Boolean {
        if (authentication == null || targetDomainObject == null || permission == null || permission !is String) {
            return false
        }

        val targetType: String = targetDomainObject::class.simpleName?.uppercase() ?: "";
        return hasPermission(authentication, targetType, permission);


    }

    override fun hasPermission(
        authentication: Authentication?,
        targetId: Serializable?,
        targetType: String?,
        permission: Any?
    ): Boolean {
        TODO("Not yet implemented")
    }

    private fun hasPermission(
        authentication: Authentication,
        targetType: String,
        permission: String): Boolean {
        for (grantedAuth in authentication.authorities) {
            if (grantedAuth.authority.startsWith(targetType) && grantedAuth.authority.contains(permission)) {
                return true
            }
        }
        return false
    }
}