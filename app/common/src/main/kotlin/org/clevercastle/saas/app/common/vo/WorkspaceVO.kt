package org.clevercastle.saas.app.common.vo

import org.clevercastle.saas.core.account.UserWorkspace
import org.clevercastle.saas.core.account.Workspace
import org.clevercastle.saas.core.model.account.UserWorkspaceRole

class WorkspaceVO {
    companion object {
        fun fromWorkspace(workspace: Workspace): WorkspaceVO {
            return WorkspaceVO().apply {
                this.id = workspace.id
                this.name = workspace.name
            }
        }
    }

    lateinit var id: String
    var name: String? = null
}

class UserWorkspaceVO {
    companion object {
        fun fromUserWorkspace(userWorkspace: UserWorkspace): UserWorkspaceVO {
            return UserWorkspaceVO().apply {
                this.workspaceId = userWorkspace.workspaceId
                this.workspaceName = userWorkspace.workspaceName
                this.workspaceUserId = userWorkspace.workspaceUserId
                this.workspaceUserName = userWorkspace.workspaceUserName
                this.role = userWorkspace.role
            }
        }
    }

    lateinit var workspaceId: String
    lateinit var workspaceName: String
    lateinit var workspaceUserId: String
    lateinit var workspaceUserName: String
    lateinit var role: UserWorkspaceRole
}