package com.github.nenadjakic.eav.controller

import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam

interface EavController<RQ, RE> {
    @GetMapping
    fun findAll(): ResponseEntity<List<RE>>

    @GetMapping("/page")
    fun findPage(@RequestParam pageNumber: Int, @RequestParam(required = false) pageSize: Int?): ResponseEntity<Page<RE>>

    @GetMapping("/{id}")
    fun findById(id: Long): ResponseEntity<RE>

    @PostMapping
    fun create(dto:RQ): ResponseEntity<Void>

    @PutMapping
    fun update(dto:RQ): ResponseEntity<Void>

    @DeleteMapping
    fun deleteById(id: Long): ResponseEntity<Void>
}