package com.github.nenadjakic.eav.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull

class RegisterRequest {
    @NotNull
    @Email
    lateinit var email: String

    @NotNull
    lateinit var password: String


}
