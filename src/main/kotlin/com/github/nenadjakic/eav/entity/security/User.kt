package com.github.nenadjakic.eav.entity.security

import com.github.nenadjakic.eav.entity.AbstractEntityId
import jakarta.persistence.*
import java.time.OffsetDateTime

/**
 * Represents a user entity in the security system.
 * This class is mapped to the "user" table in the "security" schema.
 */
@Entity
@Table(schema = "security", name = "user")
class User : AbstractEntityId<Long>() {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "security.user_id_seq")
    @SequenceGenerator(schema = "security", name = "user_id_seq", allocationSize = 1)
    @Column(name = "id")
    override var id: Long? = null

    @Column(name = "username", unique = true, nullable = false, length = 200)
    lateinit var username: String

    @Column(name = "password", nullable = false, length = 200)
    lateinit var password: String

    @Column(name = "expire_at", nullable = false)
    lateinit var expireAt: OffsetDateTime

    @Column(name = "enabled", nullable = false)
    var enabled: Boolean = false

    @Column(name = "email", unique = true, nullable = false, length = 500)
    lateinit var email: String

    @Column(name = "email_confirmed", nullable = false)
    var emailConfirmed: Boolean = false

    @ManyToMany
    @JoinTable(
        schema = "security", name = "user_role",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    private val _roles: MutableSet<Role> = mutableSetOf()
    var roles: Set<Role>
        get() = _roles.toSet()
        set(value) {
            _roles.clear()
            _roles.addAll(value)
        }
    fun addRole(role: Role): Boolean {
        return _roles.add(role)
    }
}