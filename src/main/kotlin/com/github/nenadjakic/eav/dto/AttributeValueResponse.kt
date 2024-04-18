package com.github.nenadjakic.eav.dto

data class AttributeValueResponse(
    val entityId: Long,
    val entityName: String,
    val attributeId: Long,
    val attributeName: String,
    val value: Any
)