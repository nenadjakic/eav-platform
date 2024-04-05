package com.github.nenadjakic.eav.entity

import jakarta.persistence.Column

/**
 * Represents an abstract entity with a "deleted" flag.
 * This class extends AbstractEntityId to include an identifier for the entity.
 *
 * @param T The type of the entity's identifier.
 */
abstract class AbstractEntityDeleted<T> : AbstractEntityId<T>() {

    /**
     * Indicates whether the entity is deleted or not.
     * By default, entities are considered not deleted.
     */
    @Column(name = "deleted", nullable = false)
    var deleted: Boolean = false
}