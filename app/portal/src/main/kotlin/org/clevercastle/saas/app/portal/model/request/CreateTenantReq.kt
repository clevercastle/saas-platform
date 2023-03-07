package org.clevercastle.saas.app.portal.model.request

import javax.validation.constraints.NotBlank

data class CreateTenantReq(
        @field:NotBlank(message = "Name is required")
        val name: String?,
        @field:NotBlank(message = "User in tenant's name  is required")
        val tenantUserName: String?
)
