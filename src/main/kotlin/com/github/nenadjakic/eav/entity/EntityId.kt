package com.github.nenadjakic.eav.entity

import jakarta.persistence.*
import jakarta.persistence.Entity
import org.springframework.context.annotation.Description

/**
 * Represents an entity identifier.
 * This class is mapped to the "entity" table in the "public" schema.
 */
@Entity
@Table(schema = "public", name = "entity")
class EntityId  : AbstractEntityId<Long>() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_id_seq")
    @SequenceGenerator(name = "entity_id_seq", allocationSize = 1)
    @Column(name = "id")
    override var id: Long? = null

    @Column(name = "name")
    lateinit var name: String

    @Column(name = "description")
    var description: String? = null
}