package com.github.nenadjakic.eav.extension

import org.modelmapper.ModelMapper
import java.util.function.Consumer

private fun <S, T> map(modelMapper: ModelMapper, source: S, type: Class<T>?): T = modelMapper.map(source, type)


fun <S, T> ModelMapper.collectionMap(source: List<S>?, type: Class<T>?): List<T> {
    val result: MutableList<T> = ArrayList()
    source?.forEach(Consumer { result.add(map(this, it, type)) })
    return result
}
