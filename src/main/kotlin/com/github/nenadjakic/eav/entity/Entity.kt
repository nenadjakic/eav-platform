package com.github.nenadjakic.eav.entity

import jakarta.persistence.*
import jakarta.persistence.Entity

/**
 * Represents an entity identifier.
 * This class is mapped to the "entity" table in the "public" schema.
 */
@Entity
@Table(schema = "public", name = "entity",
    uniqueConstraints = [
        UniqueConstraint(name = "uq_entity_name", columnNames = ["name"])
    ])
class Entity  : AbstractEntityId<Long>() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_id_seq")
    @SequenceGenerator(name = "entity_id_seq", allocationSize = 1)
    @Column(name = "id")
    @PrimaryKeyJoinColumn
    override var id: Long? = null

    @Column(name = "name", nullable = false, unique = true)
    lateinit var name: String

    @Column(name = "description")
    var description: String? = null

    @OneToMany(mappedBy = "entity")
    private val _attributes: MutableSet<Attribute> = mutableSetOf()

    var attributes: Set<Attribute>
        get() = _attributes.toSet()
        set(value) {
            _attributes.clear()
            _attributes.addAll(value)
        }

    fun addAttribute(attribute: Attribute): Boolean = _attributes.add(attribute)
}