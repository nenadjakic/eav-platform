package com.github.nenadjakic.eav.entity.security

import com.github.nenadjakic.eav.entity.Attribute
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

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
}