package com.github.nenadjakic.eav.security

import com.github.nenadjakic.eav.entity.security.Action
import com.github.nenadjakic.eav.entity.security.AttributePermission
import com.github.nenadjakic.eav.security.model.SecurityUser
import com.github.nenadjakic.eav.service.security.AttributePermissionService
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

/**
 * Service class for attribute-based security operations.
 *
 * @param attributePermissionService The service for managing attribute permissions
 */
@Service
class AttributeSecurityService(
    private val attributePermissionService: AttributePermissionService
) {
    @Deprecated(message = "Test purpose only. Use canRead(attributeId: Long).")
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

    /**
     * Checks if the user has permission to read an attribute-value for given attribute.
     *
     * @param attributeId The ID of the attribute to check for read permission
     * @return true if the user has permission to read the attribute-value, otherwise false
     */
    fun canRead(attributeId: Long): Boolean = hasPermission(attributeId, Action.READ)

    /**
     * Checks if the user has permission to create an attribute-value for the given attribute.
     *
     * @param attributeId The ID of the attribute to check for creation permission
     * @return true if the user has permission to create the attribute-value, otherwise false
     */
    fun canCreate(attributeId: Long): Boolean = hasPermission(attributeId, Action.CREATE)

    /**
     * Checks if the user has permission to update an attribute-value for the given attribute.
     *
     * @param attributeId The ID of the attribute to check for update permission
     * @return true if the user has permission to update the attribute-value, otherwise false
     */
    fun canUpdate(attributeId: Long): Boolean = hasPermission(attributeId, Action.UPDATE)


    /**
     * Checks if the user has permission to delete an attribute-value for the given attribute.
     *
     * @param attributeId The ID of the attribute to check for delete permission
     * @return true if the user has permission to delete the attribute-value, otherwise false
     */
    fun canDelete(attributeId: Long): Boolean = hasPermission(attributeId, Action.DELETE)

    /**
     * Checks if the user has permission to perform a specific action on an attribute-value for the given attribute.
     *
     * @param attributeId The ID of the attribute to check for permission
     * @param action The action to check permission for (e.g., READ, CREATE, UPDATE, DELETE)
     * @return true if the user has permission to perform the action, otherwise false
     */
    private fun hasPermission(attributeId: Long, action: Action): Boolean {
        var hasPermission = false
        for (role in getRoles(getUser() ?: throw Exception("User does not exists."))) {
            hasPermission = hasPermission(role, attributeId, action)
            if (hasPermission) {
                break
            }
        }
        return hasPermission
    }

    /**
     * Checks if a user with the specified role has permission to perform a specific action
     * on an attribute-value for the given attribute.
     *
     * @param role The role of the user to check for permission
     * @param attributeId The ID of the attribute to check for permission
     * @param action The action to check permission for (e.g., READ, CREATE, UPDATE, DELETE)
     * @return true if the user with the specified role has permission to perform the action, otherwise false
     */
    private fun hasPermission(role: String, attributeId: Long, action: Action): Boolean = hasPermission(attributePermissionService.findByRoleNameAndAttributeId(role, attributeId), action)

    /**
     * Checks if the attribute permission allows a specific action to be performed.
     *
     * @param attributePermission The attribute permission object to check
     * @param action The action to check permission for (e.g., READ, CREATE, UPDATE, DELETE)
     * @return true if the attribute permission allows the action, otherwise false
     */
    private fun hasPermission(attributePermission: AttributePermission?, action: Action): Boolean {
        return attributePermission != null && attributePermission.actions.contains(action)
    }

    /**
     * Retrieves the current authenticated user from the security context.
     *
     * @return The authenticated user as a SecurityUser object, or null if not authenticated
     */
    private fun getUser(): SecurityUser? {
        return if (SecurityContextHolder.getContext().authentication == null) {
            null
        } else {
            (SecurityContextHolder.getContext().authentication.principal as SecurityUser?)
        }
    }

    /**
     * Retrieves the roles associated with the provided UserDetails.
     *
     * @param user The UserDetails object for which roles are to be retrieved
     * @return A list of roles (as strings) associated with the user
     */
    private fun getRoles(user: UserDetails): List<out String> = user.authorities.map { it.authority }
}