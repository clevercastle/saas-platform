package org.clevercastle.saas.core.internal.exception

import javax.ws.rs.core.Response

class NotFoundException(message: String) : HttpResponseException(httpStatus = Response.Status.NOT_FOUND.statusCode, null, message)

class BadRequestException(message: String) : HttpResponseException(httpStatus = Response.Status.BAD_REQUEST.statusCode, null, message)