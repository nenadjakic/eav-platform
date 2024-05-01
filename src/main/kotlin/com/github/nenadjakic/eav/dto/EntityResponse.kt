package com.github.nenadjakic.eav.dto

data class EntityResponse(
    val id: Long,
    val entityType: EntityTypeSimpleResponse,
    val code: String,
    val description: String?,
    val attributeValues: List<AttributeValueSimpleResponse>
)