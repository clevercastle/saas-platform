package org.clevercastle.saas.base

import javax.persistence.AttributeConverter

open class EnumConverter<T : Enum<*>>(private val clazz: Class<T>) : AttributeConverter<T, String?> {

    override fun convertToDatabaseColumn(attribute: T?): String? {
        if (attribute == null) {
            return null
        }
        return attribute.name
    }

    override fun convertToEntityAttribute(dbData: String?): T? {
        val enums: Array<T> = clazz.enumConstants
        for (e: T in enums) {
            if (e.name.equals(dbData, ignoreCase = false)) {
                return e
            }
        }
        return null
    }
}