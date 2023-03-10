package org.clevercastle.saas.core.exception

class HttpResponseException(
        val httpStatus: Int = 0,
        val code: HttpResponseCode = "",
        message: String? = null,
        val detail: String? = null,
        val data: Any? = null,
) : RuntimeException(message)