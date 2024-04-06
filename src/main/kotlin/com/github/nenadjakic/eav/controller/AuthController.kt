package com.github.nenadjakic.eav.controller

import com.github.nenadjakic.eav.dto.RegisterRequest
import com.github.nenadjakic.eav.dto.SignInRequest
import com.github.nenadjakic.eav.dto.TokenResponse
import com.github.nenadjakic.eav.security.JwtService
import com.github.nenadjakic.eav.security.model.SecurityUser
import org.springframework.security.core.Authentication
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService
) {

    @PostMapping("/register")
    fun register(registerRequest: RegisterRequest): ResponseEntity<Void> {
        TODO()
    }

    @PostMapping("/signin")
    fun signIn(
        @RequestBody signInRequest: SignInRequest
    ): ResponseEntity<TokenResponse> {
        val usernamePassword = UsernamePasswordAuthenticationToken(signInRequest.username, signInRequest.password)
        val authUser: Authentication? = authenticationManager.authenticate(usernamePassword)
        val accessToken: String = jwtService.createToken(authUser?.principal as SecurityUser)

        return ResponseEntity.ok(TokenResponse(accessToken, ""))
    }

    @GetMapping("/test")
    fun ff(): String {
        return "ResponseEntity.ok().build()"
    }
}