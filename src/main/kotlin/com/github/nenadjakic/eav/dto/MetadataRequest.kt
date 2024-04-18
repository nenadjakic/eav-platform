package com.github.nenadjakic.eav.dto

import com.github.nenadjakic.eav.entity.DataType
import com.github.nenadjakic.eav.validator.MinMax
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

class MetadataRequest {
    @NotNull
    lateinit var dataType: DataType

    @Schema(defaultValue = "false")
    var required: Boolean = false

    @Schema(defaultValue = "false")
    var repeatable: Boolean = false

    @MinMax
    var minMaxValue: MinMaxRequest<Double>? = null

    @MinMax
    var minMaxLength: MinMaxRequest<Int>? = null
}
