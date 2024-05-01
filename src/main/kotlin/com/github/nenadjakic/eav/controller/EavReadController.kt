package com.github.nenadjakic.eav.controller

import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

/**
 * Interface for reading operations in the EAV (Entity-Attribute-Value) system.
 * Provides methods for retrieving entities and their attributes.
 *
 * @param <RE> The type representing the response entity.
 */
interface EavReadController<RE> {

    @PreAuthorize("hasRole('READER')")
    //@PostAuthorize("@attributeSecurityService.canRead(1L)")
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(): ResponseEntity<List<RE>>

    @PreAuthorize("hasRole('READER')")
    @GetMapping(value = ["/page"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findPage(@RequestParam pageNumber: Int, @RequestParam(required = false) pageSize: Int?): ResponseEntity<Page<RE>>

    @PreAuthorize("hasRole('READER')")
    @GetMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findById(@PathVariable id: Long): ResponseEntity<RE>
}