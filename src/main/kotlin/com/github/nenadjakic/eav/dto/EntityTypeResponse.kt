package com.github.nenadjakic.eav.dto

data class EntityTypeResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val attributes: List<AttributeResponse>
)
