package com.github.nenadjakic.eav.controller

import org.springframework.http.ResponseEntity
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
interface EavWriteController<CR, UR> {

    @PostMapping
    fun create(@RequestBody model:CR): ResponseEntity<Void>

    @PutMapping
    fun update(@RequestBody model:UR): ResponseEntity<Void>

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Void>
}