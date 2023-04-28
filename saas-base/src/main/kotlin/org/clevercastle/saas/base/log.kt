@file:JvmName("LoggerUtil")

package org.clevercastle.saas.base


import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.function.Supplier
import kotlin.reflect.KClass

fun getLogger(clz: KClass<*>): Logger {
    return LoggerFactory.getLogger(clz.java)
}

fun Logger.debugf(message: String, vararg arguments: Supplier<Any>) {
    if (this.isDebugEnabled) {
        this.debug(message, *arguments.map { it.get() }.toTypedArray())
    }
}

fun Logger.infof(message: String, vararg arguments: Supplier<Any>) {
    if (this.isInfoEnabled) {
        this.info(message, *arguments.map { it.get() }.toTypedArray())
    }
}

fun Logger.warnf(message: String, vararg arguments: Supplier<String>) {
    if (this.isWarnEnabled) {
        this.warn(message, *arguments.map { it.get() }.toTypedArray())
    }
}

fun Logger.errorf(message: String, vararg arguments: Supplier<String>, e: Throwable? = null) {
    if (this.isErrorEnabled) {
        this.error(message, *arguments.map { it.get() }.toTypedArray(), e)
    }
}

fun Logger.errorf(errorCode: LogErrorCode, message: String, vararg arguments: Supplier<String>, e: Throwable? = null) {
    if (this.isErrorEnabled) {
        this.error(message, *arguments.map { it.get() }.toTypedArray(), e)
    }
}

fun Logger.errorf(message: String, e: Throwable) {
    if (this.isErrorEnabled) {
        this.error(message, e)
    }
}

fun Logger.errorf(errorCode: LogErrorCode, message: String, e: Throwable) {
    if (this.isErrorEnabled) {
        this.error(message, e)
    }
}

enum class LogErrorCode {
    JsonProcessException,
}