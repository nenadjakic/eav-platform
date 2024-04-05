package com.github.nenadjakic.eav.entity.security

import com.github.nenadjakic.eav.entity.AbstractEntityId
import jakarta.persistence.*

/**
 * Represents a role entity in the security system.
 * This class is mapped to the "role" table in the "security" schema.
 */
@Entity
@Table(schema = "security", name = "role")
class Role : AbstractEntityId<Long>() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "security.role_id_seq")
    @SequenceGenerator(schema = "security", name = "role_id_seq", allocationSize = 1)
    @Column(name = "id")
    override var id: Long? = null

    @Column(name = "name")
    lateinit var name: String

    @ManyToMany
    @JoinTable(schema = "security", name = "role_permission",
        joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "permission_id", referencedColumnName = "id")])
    private val _permissions: MutableList<Permission> = mutableListOf()

    @OneToMany(mappedBy = "roleAttributeId.role")
    private val _attributePermissions: MutableList<AttributePermission> = mutableListOf()

    var permissions: List<Permission>
        get() = _permissions.toList()
        set(value) {
            _permissions.clear()
            _permissions.addAll(value)
        }

    var attributePermissions: List<AttributePermission>
        get() = _attributePermissions.toList()
        set(value) {
            _attributePermissions.clear()
            _attributePermissions.addAll(value)
        }
}