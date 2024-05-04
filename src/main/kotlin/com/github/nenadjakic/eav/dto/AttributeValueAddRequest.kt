package com.github.nenadjakic.eav.dto

import com.github.nenadjakic.eav.validator.ExistsAttributeId
import com.github.nenadjakic.eav.validator.ValidAttributeForEntity
import com.github.nenadjakic.eav.validator.ValidAttributeValue
import jakarta.validation.constraints.NotNull

@ValidAttributeValue
@ValidAttributeForEntity
class AttributeValueAddRequest {
    @NotNull
    var entityId: Long? = null

    @NotNull
    @ExistsAttributeId
    var attributeId: Long? = null

    var value: String? = null
}