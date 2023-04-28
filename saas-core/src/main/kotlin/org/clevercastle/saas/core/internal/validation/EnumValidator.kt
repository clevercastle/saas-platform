package org.clevercastle.saas.core.internal.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass


/**
 * @author XueJuncheng
 * @date 2022/11/10
 * user for spring-validation
 */
@MustBeDocumented @Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Constraint(validatedBy = [EnumValidatorImpl::class])
annotation class EnumValidator(
        /**
         * enum type
         */
        val value: KClass<*>,
        /**
         * wrong message
         *
         * @return
         */
        val message: String = "invalid enum value",
        /**
         * the method to get enum name
         */
        val method: String = "name", val groups: Array<KClass<*>> = [], val payload: Array<KClass<out Payload>> = [])