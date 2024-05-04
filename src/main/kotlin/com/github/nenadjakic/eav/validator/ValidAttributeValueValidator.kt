package com.github.nenadjakic.eav.validator

import com.github.nenadjakic.eav.dto.AttributeValueAddRequest
import com.github.nenadjakic.eav.entity.DataType
import com.github.nenadjakic.eav.service.AttributeService
import com.github.nenadjakic.eav.service.EntityTypeService
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ValidAttributeValueValidator(
    private val attributeService: AttributeService,
    private val entityTypeService: EntityTypeService
) : ConstraintValidator<ValidAttributeValue, AttributeValueAddRequest> {

    override fun isValid(value: AttributeValueAddRequest, context: ConstraintValidatorContext?): Boolean {
        if (value.value == null && value.attributeId == null) {
            return true
        }

        val attribute = attributeService.findById(value.attributeId!!);
        if (attribute != null) {
            value.value?.let {
                if (null == when (attribute.metadata.dataType) {
                    DataType.INTEGER -> it.toIntOrNull()
                    DataType.BOOLEAN -> it.toBooleanStrictOrNull()
                    DataType.DECIMAL -> it.toFloatOrNull()
                    DataType.DATE -> {
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        try {
                            LocalDate.parse(it, formatter)
                        } catch (e: Exception) {
                            null
                        }
                    }

                    DataType.TIME -> {
                        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")
                        try {
                            LocalTime.parse(it, formatter)
                        } catch (e: Exception) {
                            null
                        }
                    }

                    DataType.DATETIME -> {
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss.SSS")
                        try {
                            LocalDateTime.parse(it, formatter)
                        } catch (e: Exception) {
                            null
                        }
                    }

                    else -> 100
                }) {
                    context!!.disableDefaultConstraintViolation()
                    context.buildConstraintViolationWithTemplate("{validAttributeValue}")
                        .addPropertyNode("value")
                        .addConstraintViolation()

                    return false
                }
            }
        }
        return true
    }
}