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
class RoleAttributeId (
    @ManyToOne
    @JoinColumn(name = "role_id")
    val role: Role,

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    val attribute: Attribute,
)