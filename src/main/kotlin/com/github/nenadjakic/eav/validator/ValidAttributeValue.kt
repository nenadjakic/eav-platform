package com.github.nenadjakic.eav.validator

import jakarta.validation.Constraint
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass

@Target(TYPE, CLASS)
@Retention(RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [ ValidAttributeValueValidator::class ])
annotation class ValidAttributeValue(
    val message: String = "{validAttributeValue}",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Any>> = []
)
