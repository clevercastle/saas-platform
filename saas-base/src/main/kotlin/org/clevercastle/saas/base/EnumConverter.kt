package org.clevercastle.saas.base

import jakarta.persistence.AttributeConverter

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

open class MapConverter<T : Map<String, *>> : AttributeConverter<T, String?> {
    override fun convertToDatabaseColumn(attribute: T?): String? {
        if (attribute == null) {
            return null
        }
        return JsonUtils.toJson(attribute)
    }

    override fun convertToEntityAttribute(dbData: String?): T? {
        @Suppress("UNCHECKED_CAST")
        return JsonUtils.fromJson(dbData!!, Map::class) as T
    }
}

open class ListConverter<T : List<String>?> : AttributeConverter<T?, String?> {

    override fun convertToDatabaseColumn(attribute: T?): String? {
        if (attribute == null) {
            return null
        }
        return attribute.joinToString { it.toString() }
    }

    override fun convertToEntityAttribute(dbData: String?): T? {
        if (dbData == null) {
            return null
        }
        @Suppress("UNCHECKED_CAST")
        return dbData.split(",") as T
    }
}