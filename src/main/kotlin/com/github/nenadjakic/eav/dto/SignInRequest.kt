package com.github.nenadjakic.eav.dto

import jakarta.validation.constraints.NotEmpty

class SignInRequest {

    @NotEmpty
    lateinit var username: String

    @NotEmpty
    lateinit var password: String
}