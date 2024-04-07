package com.github.nenadjakic.eav.service.model

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort


class Pager private constructor(
    private var pageNumber: Int,
    private var pageSize: Int
) {
    companion object {
        private val DEFAULT_SORT: Sort = Sort.by(Sort.Direction.DESC, "id")
    }

    @JvmOverloads
    fun toPageRequest(sort: Sort? = DEFAULT_SORT): PageRequest {
        return PageRequest.of(pageNumber, pageSize, sort!!)
    }

    data class Builder(
        private var pageNumber: Int = 0,
        private var pageSize: Int = 20
    ) {
        fun withPageNumber(pageNumber: Int) = apply { this.pageNumber = pageNumber }
        fun withPageSize(pageSize: Int) = apply { this.pageSize = pageSize }

        fun build(): Pager = Pager(pageNumber, pageSize)
    }




}
