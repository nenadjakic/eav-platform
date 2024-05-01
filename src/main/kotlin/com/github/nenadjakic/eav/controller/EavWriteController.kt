package com.github.nenadjakic.eav.controller

import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

/**
 * Interface for writing operations in the EAV (Entity-Attribute-Value) system.
 * Provides methods for creating and updating entities with attributes and values.
 *
 * @param <CR> The type representing the create request.
 * @param <UR> The type representing the update request.
 */
@Validated
interface EavWriteController<CR, UR> {

    //@PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@Valid @RequestBody model:CR): ResponseEntity<Void>

    //@PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun update(@Valid @RequestBody model:UR): ResponseEntity<Void>

    //@PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Void>
}