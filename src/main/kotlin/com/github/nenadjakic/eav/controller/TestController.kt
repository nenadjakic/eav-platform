package com.github.nenadjakic.eav.controller

import com.github.nenadjakic.eav.dto.RegisterRequest
import com.github.nenadjakic.eav.entity.security.User
import com.github.nenadjakic.eav.service.security.UserService
import jakarta.validation.Valid
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/test")
@Validated
class TestController(private val modelMapper: ModelMapper, private val userService: UserService) {
    @GetMapping("/test2")
    fun test2(): String {
        return "test"
    }

    @PostMapping("/test1")
    fun test(@RequestBody registerRequest: @Valid RegisterRequest?): ResponseEntity<Void> {
        val user = modelMapper.map(registerRequest, User::class.java)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
