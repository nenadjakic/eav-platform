package com.github.nenadjakic.eav.validator

import jakarta.validation.Constraint
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass

@Target(
    CLASS
)
@Retention(RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [ ValidAttributeForEntityValidator::class ])
annotation class ValidAttributeForEntity(
    val message: String = "{validAttributeForEntity}",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Any>> = []
)
