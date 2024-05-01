package com.github.nenadjakic.eav.entity

import jakarta.persistence.*
import jakarta.persistence.Entity

/**
 * Represents an attribute entity.
 * This class is mapped to the "attribute" table in the "public" schema.
 */
@Entity
@Table(
    schema = "public",
    name = "attribute",
    uniqueConstraints = [
        UniqueConstraint(name = "uq_attribute_code", columnNames = [ "code" ]),
        UniqueConstraint(name = "uq_attribute_entity_type_id_name", columnNames = ["entity_type_id", "name"])
    ])
class Attribute : AbstractEntityId<Long>() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attribute_id_seq")
    @SequenceGenerator(name = "attribute_id_seq", allocationSize = 1)
    @Column(name = "id")
    override var id: Long? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "entity_type_id", nullable = false, updatable = false)
    lateinit var entityType: EntityType

    @Column(name = "code", nullable = false, unique = true, length = 25)
    lateinit var code: String

    @Column(name = "name", nullable = false, unique = true, length = 100)
    lateinit var name: String

    @Column(name = "description", length = 1000)
    var description: String? = null

    @OneToOne(mappedBy = "attribute", cascade = [CascadeType.ALL], optional = false)
    lateinit var metadata: Metadata
}