package com.github.nenadjakic.eav.entity

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(schema = "public", name = "attribute_value")
class AttributeValue {

    @EmbeddedId
    lateinit var entityAttributeId: EntityAttributeId

    @Column(name = "position")
    var position: Int? = null

    @Column(name = "value", columnDefinition = "varchar", length = 1000, nullable = false)
    private var _value: String? = null

    var value: Any?
        get() {
            return _value as Any
        }
        set(value) {
            _value = value.toString()
        }
}