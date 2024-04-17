package com.github.nenadjakic.eav.dto

import jakarta.validation.constraints.NotNull

class EntityAddRequest {
    @NotNull
    lateinit var name: String
    var description: String? = null

    @NotNull
    var entityTypeId: Long? = null
}