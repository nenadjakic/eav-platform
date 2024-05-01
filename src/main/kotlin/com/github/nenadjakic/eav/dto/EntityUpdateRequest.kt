package com.github.nenadjakic.eav.dto

import jakarta.validation.constraints.NotNull

class EntityUpdateRequest {

    @NotNull
    var id: Long? = null

    @NotNull
    lateinit var code: String

    @NotNull
    lateinit var name: String

    var description: String? = null
}