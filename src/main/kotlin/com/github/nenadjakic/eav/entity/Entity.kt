package com.github.nenadjakic.eav.entity

import jakarta.persistence.*
import jakarta.persistence.Entity

/**
 * Represents an entity identifier.
 * This class is mapped to the "entity" table in the "public" schema.
 */
@Entity
@Table(
    schema = "public",
    name = "entity",
    uniqueConstraints = [
        UniqueConstraint(name = "uq_entity_entity_type_id_code", columnNames = [ "entity_type_id", "code"])
    ])
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

    @Column(name = "code", nullable = false)
    lateinit var code: String

    @Column(name = "description", length = 1000)
    var description: String? = null

    @OneToMany(mappedBy = "entity")
    private val _attributeValues: MutableList<AttributeValue> = mutableListOf()

    var attributeValues: List<AttributeValue>
        get() = _attributeValues
        set(value) {
            _attributeValues.clear()
            _attributeValues.addAll(value)
        }

    fun addAttributeValue(attributeValue: AttributeValue): Boolean = _attributeValues.add(attributeValue)
}