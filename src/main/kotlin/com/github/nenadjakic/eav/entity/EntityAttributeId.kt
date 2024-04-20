package com.github.nenadjakic.eav.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

/**
 * Represents an embeddable identifier for the relationship between Entity and Attribute.
 * This class is used as an embedded id in the EntityAttribute entity.
 */
@Embeddable
class EntityAttributeId {

    @Column(name = "entity_id", nullable = false)
    var entityId: Long? = null

    @Column(name = "attribute_id", nullable = false)
    var attributeId: Long? = null
}