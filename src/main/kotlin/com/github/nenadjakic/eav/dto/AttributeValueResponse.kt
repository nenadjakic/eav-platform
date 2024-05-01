package com.github.nenadjakic.eav.dto

data class EntitySimpleResponse(
    val entityId: Long,
    val code: String,
    val description: String?
)

data class AttributeSimpleResponse(
    val attributeId: Long,
    val code: String,
    val attributeName: String,
    val description: String?
)

data class AttributeValueSimpleResponse(
    val attribute : AttributeSimpleResponse,
    val value: Any?
)

data class AttributeValueResponse(
    val entitiy: EntitySimpleResponse,
    //TODO. Should be list
    val attributes: AttributeSimpleResponse,
    val value: Any
)