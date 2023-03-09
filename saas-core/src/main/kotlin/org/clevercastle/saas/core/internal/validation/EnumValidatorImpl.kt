package org.clevercastle.saas.core.internal.validation

import org.apache.commons.lang3.StringUtils
import java.lang.reflect.InvocationTargetException
import java.util.*
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

/**
 * Case Insensitive
 */
class EnumValidatorImpl : ConstraintValidator<EnumValidator, String> {
    private val values = mutableListOf<String>()
    override fun initialize(constraintAnnotation: EnumValidator) {
        val enumClazz: KClass<*> = constraintAnnotation.value
        val enumConstants: Array<out Any> = enumClazz.java.enumConstants
        val method = try {
            enumClazz.java.getMethod(constraintAnnotation.method)
        } catch (ex: NoSuchMethodException) {
            // TODO: change exception type
            throw RuntimeException(ex)
        }
        try {
            for (enumConstant: Any? in enumConstants) {
                values.add((method.invoke(enumConstant) as String).uppercase(Locale.getDefault()))
            }
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Fail to get enum value", e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException("Fail to get enum value", e)
        }
    }

    override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
        if (StringUtils.isBlank(value)) {
            return false
        }
        return values.contains(value.uppercase(Locale.getDefault()))
    }
}