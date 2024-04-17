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
@Constraint(validatedBy = [ DoubleMinMaxValidator::class, IntMinMaxValidator::class ])
annotation class MinMax(
    val message: String = "Invalid minimum and maximum values",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Any>> = []
)