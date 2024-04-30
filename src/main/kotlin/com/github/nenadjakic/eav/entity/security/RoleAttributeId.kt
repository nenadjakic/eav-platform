package com.github.nenadjakic.eav.entity.security

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

/**
 * Represents an embeddable identifier for the relationship between Role and Attribute.
 * This class is used as an embedded id in the RoleAttribute entity.
 */
@Embeddable
class RoleAttributeId {

    @Column(name = "role_id", nullable = false)
    var roleId: Long? = null

    @Column(name = "attribute_id", nullable = false)
    var attributeId: Long? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RoleAttributeId

        if (roleId != other.roleId) return false
        if (attributeId != other.attributeId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = roleId?.hashCode() ?: 0
        result = 31 * result + (attributeId?.hashCode() ?: 0)
        return result
    }
}