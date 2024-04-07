package com.github.nenadjakic.eav.service

import com.github.nenadjakic.eav.service.model.Pager
import org.springframework.data.domain.Page

interface EavService<T> {

    fun findById(id: Long): T?

    fun findAll(): List<T>

    fun findPage(pager: Pager): Page<T>

    fun create(entity: T): T

    fun update(entity: T): T

    fun delete(entity: T)

    fun deleteById(id: Long)
}