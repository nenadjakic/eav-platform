package com.github.nenadjakic.eav.service.security

import com.github.nenadjakic.eav.entity.EntityId
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service
class EntityService {

    @PreAuthorize("hasPermission(#entity, 'read')")
    fun save(entity: EntityId) : EntityId {
        return EntityId()
    }
}