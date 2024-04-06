package com.github.nenadjakic.eav.entity

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(schema = "public", name = "attribute_value")
class AttributeValue {

    @EmbeddedId
    lateinit var entityAttributeId: EntityAttributeId

    @Column(name = "position")
    var position: Int? = null

    @Column(name = "value")
    lateinit var value: String
}