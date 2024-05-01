package com.github.nenadjakic.eav.dto

data class AttributeResponse(
    val id: Long,
    val code: String,
    val name: String,
    val description: String?,
    val metadata: MetadataResponse
)