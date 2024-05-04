package com.github.nenadjakic.eav.entity

import jakarta.persistence.*
import jakarta.persistence.Entity

@Entity
@Table(
    schema = "public",
    name = "entity_type",
    uniqueConstraints = [
        UniqueConstraint(name = "uq_entity_type_code", columnNames = [ "code" ]),
        UniqueConstraint(name = "uq_entity_type_name", columnNames = [ "name" ])
    ])
class EntityType : AbstractEntityId<Long>() {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_type_id_seq")
    @SequenceGenerator(name = "entity_type_id_seq", allocationSize = 1)
    @Column(name = "id")
    override var id: Long? = null

    @Column(name = "code", nullable = false, unique = true, length = 25)
    lateinit var code: String

    @Column(name = "name", nullable = false, unique = true, length = 100)
    lateinit var name: String

    @Column(name = "description", length = 1000)
    var description: String? = null

    @OneToMany(mappedBy = "entityType")
    lateinit var entities: MutableList<com.github.nenadjakic.eav.entity.Entity>

    @OneToMany(mappedBy = "entityType")
    private val _attributes: MutableSet<Attribute> = mutableSetOf()

    var attributes: Set<Attribute>
        get() = _attributes.toSet()
        set(value) {
            _attributes.clear()
            _attributes.addAll(value)
        }

    fun addAttribute(attribute: Attribute): Boolean = _attributes.add(attribute)

}