package org.clevercastle.saas.app.common.internal.exception

import org.clevercastle.saas.core.internal.exception.HttpResponseException
import org.slf4j.LoggerFactory
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider


@Provider
class GlobalExceptionHandler : ExceptionMapper<Exception> {
    companion object {
        private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }

    override fun toResponse(exception: Exception): Response? {
        return mapExceptionToResponse(exception)
    }

    private fun mapExceptionToResponse(exception: java.lang.Exception): Response? {
        return when (exception) {
            is HttpResponseException -> {
                // Overwrite error message
                Response.status(exception.httpStatus)
                        .entity(ErrorResponse.Builder()
                                .code(exception.code)
                                .error(exception.message)
                                .detail(exception.detail)
                                .data(exception.data)
                                .build())
                        .build()
            }
            else -> {
                log.error("[Internal Server Error]", exception)
                Response.serverError().entity("Internal Server Error").build()
            }
        }
    }

}