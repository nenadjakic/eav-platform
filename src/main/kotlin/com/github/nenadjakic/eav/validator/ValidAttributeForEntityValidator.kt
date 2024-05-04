package com.github.nenadjakic.eav.validator

import com.github.nenadjakic.eav.dto.AttributeValueAddRequest
import com.github.nenadjakic.eav.service.EntityTypeService
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class ValidAttributeForEntityValidator(
    private val entityTypeService: EntityTypeService
) : ConstraintValidator<ValidAttributeForEntity, AttributeValueAddRequest> {
    override fun isValid(value: AttributeValueAddRequest, context: ConstraintValidatorContext?): Boolean {

        if (value.entityId != null && value.attributeId != null) {
            if (!entityTypeService.existsByEntityIdAndAttributeId(value.entityId!!, value.attributeId!!)) {
                context!!.disableDefaultConstraintViolation()
                context.buildConstraintViolationWithTemplate("{validAttributeForEntity}")
                    .addPropertyNode("enitityId")
                    .addPropertyNode("attributeId")
                    .addConstraintViolation()
                return false
            }
        }
        return true
    }
}