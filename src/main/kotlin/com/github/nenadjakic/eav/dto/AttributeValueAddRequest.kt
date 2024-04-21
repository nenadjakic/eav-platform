package com.github.nenadjakic.eav.dto

import jakarta.validation.constraints.NotNull

class AttributeValueAddRequest {
    @NotNull
    var entityId: Long? = null

    @NotNull
    var attributeId: Long? = null

    var value: String? = null
}