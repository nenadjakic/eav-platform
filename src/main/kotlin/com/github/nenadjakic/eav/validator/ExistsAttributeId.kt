package com.github.nenadjakic.eav.validator

import jakarta.validation.Constraint
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass

@Target(
    FUNCTION,
    FIELD,
    ANNOTATION_CLASS,
    CONSTRUCTOR,
    VALUE_PARAMETER,
    TYPE_PARAMETER,
    PROPERTY_GETTER,
    PROPERTY_SETTER
)
@Retention(RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [ ExistsAttributeIdValidator::class ])
annotation class ExistsAttributeId(
    val message: String = "{existsAttributeId}",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Any>> = []
)
