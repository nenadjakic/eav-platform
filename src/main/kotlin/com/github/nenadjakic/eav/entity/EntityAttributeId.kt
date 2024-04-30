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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EntityAttributeId

        if (entityId != other.entityId) return false
        if (attributeId != other.attributeId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = entityId?.hashCode() ?: 0
        result = 31 * result + (attributeId?.hashCode() ?: 0)
        return result
    }

}