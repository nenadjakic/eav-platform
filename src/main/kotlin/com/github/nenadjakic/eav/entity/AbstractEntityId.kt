package com.github.nenadjakic.eav.entity

/**
 * Abstract class representing a base entity with id property.
 *
 * @param T The type of the ID value.
 */
abstract class AbstractEntityId<T> {
    abstract var id: T?
}