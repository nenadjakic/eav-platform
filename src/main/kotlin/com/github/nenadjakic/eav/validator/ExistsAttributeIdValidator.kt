package com.github.nenadjakic.eav.validator

import com.github.nenadjakic.eav.service.AttributeService
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class ExistsAttributeIdValidator(
    private val attributeService: AttributeService
) : ConstraintValidator<ExistsAttributeId, Long> {
    override fun isValid(value: Long, context: ConstraintValidatorContext?): Boolean = attributeService.existsById(value)
}