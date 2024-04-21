package com.github.nenadjakic.eav.service

import com.github.nenadjakic.eav.entity.security.AttributePermission
import com.github.nenadjakic.eav.service.security.AttributePermissionService
import jakarta.annotation.PostConstruct
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachePut
import org.springframework.stereotype.Service


@Service
class CacheScheduler(
    private val cacheManager: CacheManager,
    private val attributePermissionService: AttributePermissionService
) {

    @PostConstruct
    fun init() {
        update()
    }

    fun update() {
        for (attributePermission in attributePermissionService.findAll()) {
            putToCache(attributePermission)
        }
    }

    @CachePut(value = ["attributePermissions"], key = "{ #attributePermission.role.name, #attributePermission.attribute.id }")
    private fun putToCache(attributePermission: AttributePermission): AttributePermission {
        return attributePermission
    }
}