package com.github.nenadjakic.eav.dto

data class AttributeResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val metadata: MetadataResponse
)