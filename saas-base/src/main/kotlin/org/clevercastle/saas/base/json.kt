@file:JvmName("JsonUtil")

package org.clevercastle.saas.base

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import kotlin.reflect.KClass


class JsonUtils {
    companion object {
        private val logger = getLogger(JsonUtils::class)
        fun toJson(obj: Any): String {
            val mapper = ObjectMapper()
            try {
                return mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(obj)
            } catch (e: JsonProcessingException) {
                logger.errorf(LogErrorCode.JsonProcessException, "Fail to convert object to json, object", e)
            }
            logger.errorf(LogErrorCode.JsonProcessException, "Fail to convert object to json, object")
            throw RuntimeException("Fail to convert object to json")
        }

        fun <T : Any> fromJson(json: String, clazz: KClass<T>): T {
            val mapper = ObjectMapper()
            return mapper.readValue(json, clazz.java)
        }
    }

}