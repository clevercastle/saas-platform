package org.clevercastle.saas.app.common.vo

import org.clevercastle.saas.core.account.Workspace
import org.clevercastle.saas.core.account.WorkspaceUser
import org.clevercastle.saas.core.model.account.WorkspaceUserRole

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
        fun fromUserWorkspace(workspaceUser: WorkspaceUser): UserWorkspaceVO {
            return UserWorkspaceVO().apply {
                this.workspaceId = workspaceUser.workspaceId
                this.workspaceName = workspaceUser.workspaceName
                this.workspaceUserId = workspaceUser.workspaceUserId
                this.workspaceUserName = workspaceUser.workspaceUserName
                this.role = workspaceUser.role
            }
        }
    }

    lateinit var workspaceId: String
    lateinit var workspaceName: String
    lateinit var workspaceUserId: String
    lateinit var workspaceUserName: String
    lateinit var role: WorkspaceUserRole
}