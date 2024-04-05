package com.github.nenadjakic.eav.entity

import jakarta.persistence.Embeddable
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

/**
 * Represents an embeddable identifier for the relationship between Entity and Attribute.
 * This class is used as an embedded id in the EntityAttribute entity.
 */
@Embeddable
class EntityAttributeId (

    @ManyToOne
    @JoinColumn(name = "entity_id")
    var entity: EntityId,

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    var attribute: Attribute,
)