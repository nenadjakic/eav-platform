package com.github.nenadjakic.eav.entity.security

import com.github.nenadjakic.eav.entity.AbstractEntityId
import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*
/**
 * Represents a confirmation token entity used for email confirmation.
 *
 * This entity stores information about the confirmation token associated with user registration.
 *
 */
@Entity
@Table(schema = "security", name = "confirmation_token")
class ConfirmationToken() : AbstractEntityId<Long>() {

    @Id
    @Column(name = "id")
    override var id: Long? = null

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    lateinit var user: User

    @Column(name = "token", nullable = false)
    lateinit var token: String

    @Column(name = "expire_at", nullable = false)
    lateinit var expireAt: OffsetDateTime

    constructor(user: User) : this() {
        this.user = user
        this.token = UUID.randomUUID().toString().replace("-", "")
        this.expireAt = OffsetDateTime.now().plusHours(12L)
    }
}