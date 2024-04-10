package com.github.nenadjakic.eav.controller

import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
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

    @GetMapping
    fun findAll(): ResponseEntity<List<RE>>

    @GetMapping("/page")
    fun findPage(@RequestParam pageNumber: Int, @RequestParam(required = false) pageSize: Int?): ResponseEntity<Page<RE>>

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<RE>
}