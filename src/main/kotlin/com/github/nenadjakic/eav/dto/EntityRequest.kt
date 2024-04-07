package com.github.nenadjakic.eav.dto

import jakarta.validation.constraints.NotNull

class EntityRequest {
    interface AddValidationGroup
    interface UpdateValidationGroup

    @NotNull(groups = [ UpdateValidationGroup::class ])
    var id: Long? = null

    @NotNull
    lateinit var name: String
    var description: String? = null
}