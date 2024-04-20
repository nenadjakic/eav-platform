package com.github.nenadjakic.eav.entity.security

import com.github.nenadjakic.eav.entity.Attribute
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

/**
 * Represents the mapping between attributes and permissions in the security system.
 * This entity defines the permissions associated with each attribute.
 */
@Entity
@Table(schema = "security", name = "attribute_permission")
class AttributePermission {

    @EmbeddedId
    lateinit var roleAttributeId: RoleAttributeId

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    @MapsId("roleId")
    lateinit var role: Role

    @ManyToOne
    @JoinColumn(name = "attribute_id", nullable = false)
    @MapsId("attributeId")
    lateinit var attribute: Attribute

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "actions", nullable = false)
    private val _actions: MutableSet<Action> = mutableSetOf()

    var actions: Set<Action>
        get() = _actions.toSet()
        set(value) {
            _actions.clear()
            _actions.addAll(value)
        }

    fun addAction(action: Action) {
        _actions.add(action)
    }
}
