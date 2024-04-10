package com.github.nenadjakic.eav.dto

import com.github.nenadjakic.eav.entity.DataType

data class MetadataResponse(
    val id: Long,
    val dataType: DataType,
    val required: Boolean,
    val repeatable: Boolean,
    val minValue: Double?,
    val maxValue: Double?,
    val minLength: Int?,
    val maxLength: Int?,
)
