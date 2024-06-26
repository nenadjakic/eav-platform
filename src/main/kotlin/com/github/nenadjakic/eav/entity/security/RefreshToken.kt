package com.github.nenadjakic.eav.entity.security

import com.github.nenadjakic.eav.entity.AbstractEntityId
import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*

/**
 * Represents a refresh token entity in the system.
 *
 * This class extends AbstractEntityId<Long>(), which provides a generic implementation
 * for managing entities with a Long type ID.
 */
@Entity
@Table(
    schema = "security",
    name = "refresh_token",
    uniqueConstraints = [
        UniqueConstraint(name = "uq_security_refresh_token_token", columnNames = [ "token" ])
    ])
class RefreshToken() : AbstractEntityId<Long>() {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "security.refresh_token_id_seq")
    @SequenceGenerator(schema = "security", name = "refresh_token_id_seq", allocationSize = 1)
    @Column(name = "id")
    override var id: Long? = null

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User

    @Column(name = "token", nullable = false, length = 100, unique = true)
    lateinit var token: String

    @Column(name = "expire_at", nullable = false)
    lateinit var expireAt: OffsetDateTime

    constructor(user: User) : this() {
        this.user = user
        this.token = UUID.randomUUID().toString().replace("-", "")
        this.expireAt = OffsetDateTime.now().plusHours(12L)
    }
}