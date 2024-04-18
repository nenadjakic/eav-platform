package com.github.nenadjakic.eav.entity

import jakarta.persistence.*
import jakarta.persistence.Entity

/**
 * Represents an entity identifier.
 * This class is mapped to the "entity" table in the "public" schema.
 */
@Entity
@Table(schema = "public", name = "entity")
class Entity  : AbstractEntityId<Long>() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_id_seq")
    @SequenceGenerator(name = "entity_id_seq", allocationSize = 1)
    @Column(name = "id")
    @PrimaryKeyJoinColumn
    override var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "entity_type_id", nullable = false, updatable = false)
    lateinit var entityType: EntityType
/*
    @OneToMany(mappedBy = "entity")
    private val _attributes: MutableSet<Attribute> = mutableSetOf()

    var attributes: Set<Attribute>
        get() = _attributes.toSet()
        set(value) {
            _attributes.clear()
            _attributes.addAll(value)
        }

    fun addAttribute(attribute: Attribute): Boolean = _attributes.add(attribute)

 */
}