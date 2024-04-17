package com.github.nenadjakic.eav.validator

import com.github.nenadjakic.eav.dto.MinMaxRequest
import jakarta.validation.ConstraintValidatorContext

abstract class AbstractMinMaxValidator<T>  where T : Number, T : Comparable<T> {
    fun isValid(value: MinMaxRequest<T>?, context: ConstraintValidatorContext?): Boolean {
        if (value != null) {
            if (value.min != null && value.max != null) {
                return value.min <= value.max
            }
        }
        return true
    }
}