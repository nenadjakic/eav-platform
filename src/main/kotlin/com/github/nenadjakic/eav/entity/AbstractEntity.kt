package com.github.nenadjakic.eav.entity

import com.github.nenadjakic.eav.entity.security.User
import jakarta.persistence.Column
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import java.time.OffsetDateTime

/**
 * Represents an abstract entity with common fields like created timestamp,
 * created user, last modified timestamp, and modified user.
 *
 * @param T The type of the entity's identifier.
 */
abstract class AbstractEntity<T> : AbstractEntityDeleted<T>() {

    /**
     * The timestamp when the entity was created.
     * This field is automatically set to the current time when an entity is created.
     */
    @Column(name = "created", nullable = false, insertable = true, updatable = false)
    @CreatedBy
    var created: OffsetDateTime = OffsetDateTime.now()

    /**
     * The user who created the entity.
     */
    @ManyToOne
    @JoinColumn(name = "created_user_id", nullable = false, insertable = true, updatable = false)
    @CreatedDate
    lateinit var createdBy: User

    /**
     * The timestamp when the entity was last modified.
     * This field is automatically updated to the current time when an entity is modified.
     */
    @Column(name = "last_modified", nullable = false, insertable = false, updatable = true)
    lateinit var modified: OffsetDateTime

    /**
     * The user who last modified the entity.
     */
    @ManyToOne
    @JoinColumn(name = "last_modified_user_id", nullable = false, insertable = false, updatable = true)
    lateinit var modifiedBy: User
}