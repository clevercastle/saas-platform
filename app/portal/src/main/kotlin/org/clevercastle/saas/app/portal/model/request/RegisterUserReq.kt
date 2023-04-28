package org.clevercastle.saas.app.portal.model.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class RegisterUserReq(
        @field:NotBlank(message = "Email is required")
        @field:Email(message = "Email is invalid")
        val email: String?,
        @field:NotBlank(message = "Password is required")
        val password: String?,
        @field:NotBlank(message = "Name is required")
        val name: String?
)