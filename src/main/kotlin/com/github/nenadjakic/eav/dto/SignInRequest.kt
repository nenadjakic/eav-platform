package com.github.nenadjakic.eav.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

class SignInRequest {
    enum class GrantType {
        PASSWORD,
        REFRESHTOKEN
    }

    @NotEmpty
    lateinit var username: String

    @NotEmpty
    lateinit var passwordOrRefreshToken: String

    @NotNull
    lateinit var grantType: GrantType
}