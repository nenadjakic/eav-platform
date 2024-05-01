package com.github.nenadjakic.eav.dto

import jakarta.validation.constraints.NotNull

class EntityTypeUpdateRequest {
    @NotNull
    var id: Long? = null

    @NotNull
    lateinit var code: String

    @NotNull
    lateinit var name: String

    var description: String? = null
}