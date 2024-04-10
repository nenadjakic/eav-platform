package com.github.nenadjakic.eav.dto

data class AttributeAddResponse(val id: Long, val name: String, val description: String?, val metadata: MetadataResponse)