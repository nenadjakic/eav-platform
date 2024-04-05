package com.github.nenadjakic.eav.entity.security

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
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

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "actions")
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
