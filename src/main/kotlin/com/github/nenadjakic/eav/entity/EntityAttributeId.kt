package com.github.nenadjakic.eav.entity

import jakarta.persistence.Embeddable
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

/**
 * Represents an embeddable identifier for the relationship between Entity and Attribute.
 * This class is used as an embedded id in the EntityAttribute entity.
 */
@Embeddable
class EntityAttributeId {

    @ManyToOne
    @JoinColumn(name = "entity_id")
    lateinit var entity: Entity

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    lateinit var attribute: Attribute

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EntityAttributeId

        if (entity != other.entity) return false
        if (attribute != other.attribute) return false

        return true
    }

    override fun hashCode(): Int {
        var result = entity.hashCode()
        result = 31 * result + attribute.hashCode()
        return result
    }
}