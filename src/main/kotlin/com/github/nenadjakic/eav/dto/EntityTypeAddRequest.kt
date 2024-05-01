package com.github.nenadjakic.eav.dto

import jakarta.validation.constraints.NotNull
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlRootElement

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="EntityTypeAddRequest")
class EntityTypeAddRequest {
    @NotNull
    @XmlElement(name = "Code")
    lateinit var code: String

    @NotNull
    @XmlElement(name = "Name")
    lateinit var name: String

    @XmlElement(name = "Description")
    var description: String? = null
}