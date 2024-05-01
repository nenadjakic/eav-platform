package com.github.nenadjakic.eav.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import jakarta.xml.bind.annotation.XmlElement

class AttributeAddRequest {
    @NotNull
    var entityTypeId: Long? = null

    @NotNull
    lateinit var code: String

    @NotNull
    lateinit var name: String

    var description: String? = null

    @NotNull
    @Valid
    lateinit var metadata: MetadataRequest
}