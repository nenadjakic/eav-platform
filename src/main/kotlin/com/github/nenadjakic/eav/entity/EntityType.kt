package com.github.nenadjakic.eav.entity

import jakarta.persistence.*
import jakarta.persistence.Entity

@Entity
@Table(schema = "public", name = "entity_type",
    uniqueConstraints = [
        UniqueConstraint(name = "uq_entity_type_name", columnNames = ["name"])
    ])
class EntityType : AbstractEntityId<Long>() {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_type_id_seq")
    @SequenceGenerator(name = "entity_type_id_seq", allocationSize = 1)
    @Column(name = "id")
    override var id: Long? = null

    @Column(name = "name", nullable = false, unique = true, length = 100)
    lateinit var name: String

    @Column(name = "description", length = 1000)
    var description: String? = null
}