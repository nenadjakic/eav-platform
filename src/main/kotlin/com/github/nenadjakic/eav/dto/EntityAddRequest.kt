package com.github.nenadjakic.eav.dto

import jakarta.validation.constraints.NotNull

class EntityAddRequest {
    @NotNull
    var entityTypeId: Long? = null
}