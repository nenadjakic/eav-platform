package com.github.nenadjakic.eav.security

import org.springframework.security.access.expression.SecurityExpressionRoot
import org.springframework.security.core.Authentication

class AttributeSecurityExpressionRoot(
    authentication: Authentication
) : SecurityExpressionRoot(authentication) {

    fun canRead(attribute: Long): Boolean {
        TODO()
    }

    fun canCreate(attribute: Long): Boolean {
        TODO()
    }

    fun canUpdate(attribute: Long): Boolean {
        TODO()
    }

    fun canDelete(attribute: Long): Boolean {
        TODO()
    }
}