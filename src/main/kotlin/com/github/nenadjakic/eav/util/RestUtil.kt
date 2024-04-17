package com.github.nenadjakic.eav.util

import com.github.nenadjakic.eav.service.model.Pager


class RestUtil {
    companion object {
        fun getPager(pageNumber: Int, pageSize: Int?) : Pager {
            val pager = Pager.Builder().withPageNumber(pageNumber)
            if (pageSize != null) {
                pager.withPageSize(pageSize)
            }
            return pager.build()
        }
    }
}