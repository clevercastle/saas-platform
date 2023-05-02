package org.clevercastle.saas.app.portal.model.request

import jakarta.validation.constraints.NotBlank
import org.clevercastle.saas.core.internal.validation.EnumValidator
import org.clevercastle.saas.model.core.account.AccountRole

data class JoinWorkspaceReq(
    @field:NotBlank(message = "User Id is required")
    val userId: String?,
    @field:NotBlank(message = "Name is required")
    val workspaceId: String?,
    @field:NotBlank(message = "Account name is required")
    val accountName: String?,
    @field:NotBlank(message = "role in workspace is required")
    @field:EnumValidator(AccountRole::class, message = "Invalid role", method = "name")
    val accountRole: String?
)