package com.github.nenadjakic.eav.entity.security

import com.github.nenadjakic.eav.entity.Attribute
import jakarta.persistence.Embeddable
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

/**
 * Represents an embeddable identifier for the relationship between Role and Attribute.
 * This class is used as an embedded id in the RoleAttribute entity.
 */
@Embeddable
class RoleAttributeId {

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    lateinit var role: Role

    @ManyToOne
    @JoinColumn(name = "attribute_id", nullable = false)
    lateinit var attribute: Attribute

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RoleAttributeId

        if (role != other.role) return false
        if (attribute != other.attribute) return false

        return true
    }

    override fun hashCode(): Int {
        var result = role.hashCode()
        result = 31 * result + attribute.hashCode()
        return result
    }
}