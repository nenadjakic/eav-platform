package com.github.nenadjakic.eav.entity

import jakarta.persistence.*
import jakarta.persistence.Entity
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

/**
 * Represents a metadata entity in the public schema.
 * This class is mapped to the "metadata" table in the "public" schema.
 */
@Entity
@Table(schema = "public", name = "metadata")
class Metadata : AbstractEntityId<Long>() {
    @Id
    @Column(name = "id")
    override var id: Long? = null

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    lateinit var attribute: Attribute

    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false)
    lateinit var dataType: DataType

    @Column(name = "required", nullable = false)
    var required: Boolean = false

    @Column(name = "repeatable", nullable = false)
    var repeatable: Boolean = false

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "sub_attribute_ids")
    var subAttribute: Set<Attribute>? = mutableSetOf()

    @Column(name = "min_value")
    var minValue: Double? = null

    @Column(name = "max_value")
    var maxValue: Double? = null

    @Column(name = "min_length")
    var minLength: Int? = null

    @Column(name = "max_length")
    var maxLength: Int? = null
}