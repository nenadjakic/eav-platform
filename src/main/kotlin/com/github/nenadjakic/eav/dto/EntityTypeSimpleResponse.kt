package com.github.nenadjakic.eav.dto

data class EntityTypeSimpleResponse(
    val id: Long,
    val code: String,
    val name: String,
    val description: String?,
)
