package com.github.nenadjakic.eav.util

import com.github.nenadjakic.eav.service.model.Pager
import org.modelmapper.ModelMapper
import java.util.function.Consumer


class RestUtil {
    companion object {
        fun getPager(pageNumber: Int, pageSize: Int?) : Pager {
            val pager = Pager.Builder().withPageNumber(pageNumber)
            if (pageSize != null) {
                pager.withPageSize(pageSize)
            }
            return pager.build()
        }

        fun <S, T> map(modelMapper: ModelMapper, source: S, type: Class<T>?): T = modelMapper.map(source, type)

        fun <S, T> map(modelMapper: ModelMapper, source: List<S>?, type: Class<T>?): List<T> {
            val result: MutableList<T> = ArrayList()
            source?.forEach(Consumer { result.add(map(modelMapper, it, type)) })
            return result
        }
    }
}