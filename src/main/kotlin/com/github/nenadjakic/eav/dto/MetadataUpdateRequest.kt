package com.github.nenadjakic.eav.dto

import com.github.nenadjakic.eav.entity.DataType
import jakarta.validation.constraints.NotNull

class MetadataUpdateRequest {
    @NotNull
    var id: Long? = null

    lateinit var dataType: DataType
    var required: Boolean = false
    var repeatable: Boolean = false
    var minValue: Double? = null
    var maxValue: Double? = null
    var minLength: Int? = null
    var maxLength: Int? = null
}