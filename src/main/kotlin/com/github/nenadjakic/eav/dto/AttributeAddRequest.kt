package com.github.nenadjakic.eav.dto

import jakarta.validation.constraints.NotNull

class AttributeAddRequest {
    @NotNull
    lateinit var name: String
    var description: String? = null
}