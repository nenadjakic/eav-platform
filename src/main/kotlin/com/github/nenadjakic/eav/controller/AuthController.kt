package com.github.nenadjakic.eav.controller

import com.github.nenadjakic.eav.dto.RegisterRequest
import com.github.nenadjakic.eav.dto.SignInRequest
import com.github.nenadjakic.eav.dto.TokenResponse
import com.github.nenadjakic.eav.entity.security.User
import com.github.nenadjakic.eav.security.JwtService
import com.github.nenadjakic.eav.security.model.SecurityUser
import com.github.nenadjakic.eav.service.security.RefreshTokenService
import com.github.nenadjakic.eav.service.security.UserService
import jakarta.validation.Valid
import org.modelmapper.ModelMapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.security.core.Authentication
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/auth")
@Validated
open class AuthController(
    private val modelMapper: ModelMapper,
    private val userService: UserService,
    private val refreshTokenService: RefreshTokenService,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService
) {

    /**
     * Registers a new user with the provided registration request.
     *
     * This endpoint is used to register a new user based on the provided registration request.
     * The registration request should contain valid user details such as username, email, and password.
     * The request body is validated using the @Valid annotation, ensuring that the provided data meets
     * the required validation rules defined in the RegisterRequest class.
     *
     * @param registerRequest The registration request containing user details.
     * @return ResponseEntity<Void> representing the HTTP response for the registration operation.
     *         Returns ResponseEntity.created() if the registration is successful, otherwise returns an
     *         appropriate error response.
     */
    @PostMapping("/register")
    open fun register(@Valid @RequestBody registerRequest: RegisterRequest): ResponseEntity<Void> {
        val user = modelMapper.map(registerRequest, User::class.java)
        userService.save(user)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    /**
     * Confirms user email based on the provided token.
     *
     * This endpoint is used to confirm a user's email based on the token provided as a request parameter.
     * The token is typically sent to the user's email during the registration process for email confirmation.
     * Once the token is validated, the user's email is confirmed and appropriate response is returned.
     *
     * @param token The confirmation token sent to the user's email.
     * @return ResponseEntity<String> representing the HTTP response for email confirmation.
     *         Returns ResponseEntity.ok() if the email is successfully confirmed,
     *         otherwise returns an appropriate error response.
     */
    @GetMapping("/confirm-email")
    open fun confirmEmail(@RequestParam(name = "token") token: String): ResponseEntity<String> {
        userService.confirmEmail(token)
        return ResponseEntity.ok().body("Email confirmed!")
    }

    /**
     * Signs in a user based on the provided sign-in request.
     *
     * This endpoint is used to authenticate and sign in a user based on the provided sign-in request.
     * The sign-in request should contain valid credentials such as username/email and password.
     * Upon successful authentication, a JWT token is generated and returned in the response.
     *
     * @param signInRequest The sign-in request containing user credentials.
     * @return ResponseEntity<TokenResponse> representing the HTTP response for sign-in operation.
     *         Returns ResponseEntity.ok() with a TokenResponse if authentication is successful,
     *         otherwise returns an appropriate error response.
     */
    @PostMapping("/signin")
    open fun signIn(
         @Valid @RequestBody signInRequest: SignInRequest
    ): ResponseEntity<TokenResponse> {
        if (signInRequest.grantType == SignInRequest.GrantType.PASSWORD) {
            val usernamePassword = UsernamePasswordAuthenticationToken(signInRequest.username, signInRequest.passwordOrRefreshToken)
            val authUser: Authentication? = authenticationManager.authenticate(usernamePassword)
            val accessToken: String = jwtService.createToken(authUser?.principal as SecurityUser)
            val refreshToken = refreshTokenService.create(signInRequest.username)?.token

            return ResponseEntity.ok(TokenResponse(accessToken, refreshToken))
        } else if (signInRequest.grantType == SignInRequest.GrantType.REFRESHTOKEN) {
            val refreshTokenEntity = refreshTokenService.findByUsernameAndToken(signInRequest.username, signInRequest.passwordOrRefreshToken)
            if (refreshTokenEntity != null) {
                val accessToken: String = jwtService.createToken(SecurityUser(refreshTokenEntity.user))
                val refreshToken = refreshTokenService.create(signInRequest.username)?.token

                return ResponseEntity.ok(TokenResponse(accessToken, refreshToken))
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
    }

    //open fun jtwFromRefreshToken(refreshToken: String)

    @GetMapping("/test")
    open fun ff(): String {
        return "ResponseEntity.ok().build()"
    }
}