package com.github.nenadjakic.eav.entity.security

import com.github.nenadjakic.eav.entity.AbstractEntityId
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

/**
 * Represents a permission entity within the system.
 * Permissions define general access rights or restrictions for specific entities.
 * Although this class represents the concept of permissions, it is currently not used in the system.
 *
 */
@Entity
@Table(schema = "security", name = "permission")
class Permission : AbstractEntityId<Long>() {
    @Id
    @Column(name = "id")
    override var id: Long? = null

    @Column(name = "name")
    lateinit var name: String

    @Column(name = "description")
    lateinit var description: String

    @ManyToMany(mappedBy = "_permissions")
    private val _roles: MutableList<Role> = mutableListOf()

    var roles: List<Role>
        get() = _roles.toList()
        set(value) {
            _roles.clear()
            _roles.addAll(value)
        }

    fun addRole(role: Role) {
        _roles.add(role)
    }
}