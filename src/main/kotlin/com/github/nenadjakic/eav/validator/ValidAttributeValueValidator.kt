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
            val metadata = attribute.metadata

            value.value?.let {
                val convertedValue: Any? = when (attribute.metadata.dataType) {
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
                }

                if (null == convertedValue) {
                    context!!.disableDefaultConstraintViolation()
                    context.buildConstraintViolationWithTemplate("{validAttributeValue}")
                        .addPropertyNode("value")
                        .addConstraintViolation()

                    return false
                }

                return when (metadata.dataType) {
                    DataType.INTEGER -> {
                        var result = true
                        if (metadata.minValue != null && (convertedValue!! as Int) < (metadata.minValue!! as Int)) {
                            context!!.disableDefaultConstraintViolation()
                            context.buildConstraintViolationWithTemplate("{minValue}")
                                .addPropertyNode("value")
                                .addConstraintViolation()
                            result = false
                        }
                        if (metadata.maxValue != null && (convertedValue!! as Int) > (metadata.maxValue!! as Int)) {
                            context!!.disableDefaultConstraintViolation()
                            context.buildConstraintViolationWithTemplate("{maxValue}")
                                .addPropertyNode("value")
                                .addConstraintViolation()
                            result = false
                        }

                        result
                    }
                    DataType.DECIMAL -> {
                        var result = true
                        if (metadata.minValue != null && (convertedValue!! as Float) < (metadata.minValue!! as Float)) {
                            context!!.disableDefaultConstraintViolation()
                            context.buildConstraintViolationWithTemplate("{minValue}")
                                .addPropertyNode("value")
                                .addConstraintViolation()
                            result = false
                        }
                        if (metadata.maxValue != null && (convertedValue!! as Float) > (metadata.maxValue!! as Float)) {
                            context!!.disableDefaultConstraintViolation()
                            context.buildConstraintViolationWithTemplate("{maxValue}")
                                .addPropertyNode("value")
                                .addConstraintViolation()
                            result = false
                        }

                        result
                    }
                    DataType.STRING -> {
                        var result = true
                        if (metadata.minLength != null && (convertedValue!! as String).length < metadata.minLength!!) {
                            context!!.disableDefaultConstraintViolation()
                            context.buildConstraintViolationWithTemplate("{minLengthValue}")
                                .addPropertyNode("value")
                                .addConstraintViolation()
                            result = false
                        }
                        if (metadata.maxLength != null && (convertedValue!! as String).length > metadata.maxLength!!) {
                            context!!.disableDefaultConstraintViolation()
                            context.buildConstraintViolationWithTemplate("{maxLengthValue}")
                                .addPropertyNode("value")
                                .addConstraintViolation()
                            result = false
                        }

                        result
                    }
                    else -> true
                }
            }
        }
        return true
    }
}