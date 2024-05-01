package com.github.nenadjakic.eav.dto

import jakarta.validation.constraints.NotNull

class EntityAddRequest {
    @NotNull
    var entityTypeId: Long? = null

    @NotNull
    lateinit var code: String

    var description: String? = null
}