package org.clevercastle.saas.core.internal.exception

open class HttpResponseException(
    val httpStatus: Int = 0,
    val code: HttpResponseCode? = null,
    message: String? = null,
    val detail: String? = null,
    val data: Any? = null,
) : RuntimeException(message)