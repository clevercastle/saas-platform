package org.clevercastle.saas.app.portal.model.request

import javax.validation.constraints.NotBlank

data class CreateWorkspaceReq(
        @field:NotBlank(message = "Name is required")
        val name: String?,
        @field:NotBlank(message = "User in workspace's name  is required")
        val workspaceUserName: String?
)
