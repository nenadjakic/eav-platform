package com.github.nenadjakic.eav.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

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