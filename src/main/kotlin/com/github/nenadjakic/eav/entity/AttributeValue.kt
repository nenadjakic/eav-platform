package com.github.nenadjakic.eav.entity

import jakarta.persistence.*
import jakarta.persistence.Entity
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@Entity
@Table(schema = "public", name = "attribute_value")
class AttributeValue {

    @EmbeddedId
    lateinit var entityAttributeId: EntityAttributeId

    @ManyToOne
    @JoinColumn(name = "entity_id", nullable = false)
    @MapsId("entityId")
    lateinit var entity: com.github.nenadjakic.eav.entity.Entity

    @ManyToOne
    @JoinColumn(name = "attribute_id", nullable = false)
    @MapsId("attributeId")
    lateinit var attribute: Attribute

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