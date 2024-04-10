package com.github.nenadjakic.eav.entity

import jakarta.persistence.*
import jakarta.persistence.Entity

/**
 * Represents an attribute entity.
 * This class is mapped to the "attribute" table in the "public" schema.
 */
@Entity
@Table(schema = "public", name = "attribute")
class Attribute : AbstractEntityId<Long>() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attribute_id_seq")
    @SequenceGenerator(name = "attribute_id_seq", allocationSize = 1)
    @Column(name = "id")
    override var id: Long? = null

    @Column(name = "name")
    lateinit var name: String

    @Column(name = "description")
    var description: String? = null

    @OneToOne(mappedBy = "attribute")
    lateinit var metadata: Metadata
}