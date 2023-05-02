package org.clevercastle.saas.app.common.internal.exception

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.clevercastle.saas.base.getLogger
import org.clevercastle.saas.core.internal.exception.HttpResponseException


@Provider
class GlobalExceptionHandler : ExceptionMapper<Exception> {
    companion object {
        private val log = getLogger(GlobalExceptionHandler::class)
    }

    override fun toResponse(exception: Exception): Response? {
        return mapExceptionToResponse(exception)
    }

    private fun mapExceptionToResponse(exception: java.lang.Exception): Response? {
        return when (exception) {
            is HttpResponseException -> {
                // Overwrite error message
                Response.status(exception.httpStatus)
                    .entity(
                        ErrorResponse.Builder()
                            .code(exception.code)
                            .error(exception.message)
                            .detail(exception.detail)
                            .data(exception.data)
                            .build()
                    )
                    .build()
            }

            else -> {
                log.error("[Internal Server Error]", exception)
                Response.serverError().entity("Internal Server Error").build()
            }
        }
    }

}