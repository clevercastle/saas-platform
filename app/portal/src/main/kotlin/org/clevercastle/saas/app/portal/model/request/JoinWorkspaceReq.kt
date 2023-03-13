package org.clevercastle.saas.app.portal.model.request

import org.clevercastle.saas.base.account.WorkspaceUserRole
import org.clevercastle.saas.core.internal.validation.EnumValidator
import javax.validation.constraints.NotBlank

data class JoinWorkspaceReq(
        @field:NotBlank(message = "User Id is required")
        val userId: String?,
        @field:NotBlank(message = "Name is required")
        val workspaceId: String?,
        @field:NotBlank(message = "User in workspace's name  is required")
        val workspaceUserName: String?,
        @field:NotBlank(message = "role in workspace is required")
        @field:EnumValidator(WorkspaceUserRole::class, message = "Invalid role", method = "name")
        val workspaceUserRole: String?
)