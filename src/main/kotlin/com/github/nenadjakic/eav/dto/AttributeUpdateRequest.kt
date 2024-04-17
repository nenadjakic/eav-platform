package com.github.nenadjakic.eav.dto

import jakarta.validation.constraints.NotNull

class AttributeUpdateRequest {

    @NotNull
    var id: Long? = null

    @NotNull
    lateinit var name: String
    var description: String? = null

    @NotNull
    lateinit var metadata: MetadataRequest
}