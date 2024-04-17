package com.github.nenadjakic.eav.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull

class AttributeAddRequest {
    @NotNull
    var entityId: Long? = null

    @NotNull
    lateinit var name: String
    var description: String? = null

    @NotNull
    @Valid
    lateinit var metadata: MetadataRequest
}