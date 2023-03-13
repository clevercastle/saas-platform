package org.clevercastle.saas.app.common.vo

import org.clevercastle.saas.base.account.WorkspaceUserRole
import org.clevercastle.saas.core.account.Workspace
import org.clevercastle.saas.core.account.WorkspaceTeam
import org.clevercastle.saas.core.account.WorkspaceUser
import java.time.OffsetDateTime

class WorkspaceVO {
    companion object {
        fun fromWorkspace(workspace: Workspace): WorkspaceVO {
            return WorkspaceVO().apply {
                this.id = workspace.id
                this.name = workspace.name
                this.createdAt = workspace.createdAt
                this.updatedAt = workspace.updatedAt
                this.createdBy = workspace.createdBy
                this.updatedBy = workspace.updatedBy
            }
        }
    }

    lateinit var id: String
    var name: String? = null
    lateinit var createdAt: OffsetDateTime
    lateinit var updatedAt: OffsetDateTime
    var createdBy: String? = null
    var updatedBy: String? = null
}

class UserWorkspaceVO {
    companion object {
        fun fromUserWorkspace(workspaceUser: WorkspaceUser): UserWorkspaceVO {
            return UserWorkspaceVO().apply {
                this.userId = workspaceUser.userId
                this.workspaceId = workspaceUser.workspaceId
                this.workspaceName = workspaceUser.workspaceName
                this.workspaceUserName = workspaceUser.workspaceUserName
                this.role = workspaceUser.role
            }
        }
    }

    lateinit var userId: String
    lateinit var workspaceId: String
    lateinit var workspaceName: String
    lateinit var workspaceUserName: String
    lateinit var role: WorkspaceUserRole
}

class WorkspaceTeamVO {
    companion object {
        fun fromWorkspaceTeam(workspaceTeam: WorkspaceTeam): WorkspaceTeamVO {
            return WorkspaceTeamVO().apply {
                this.id = workspaceTeam.id
                this.name = workspaceTeam.name
                this.description = workspaceTeam.description
                this.createdAt = workspaceTeam.createdAt
                this.updatedAt = workspaceTeam.updatedAt
                this.createdBy = workspaceTeam.createdBy
                this.updatedBy = workspaceTeam.updatedBy
            }
        }
    }

    lateinit var id: String
    var name: String? = null
    var description: String? = null
    lateinit var createdAt: OffsetDateTime
    lateinit var updatedAt: OffsetDateTime
    var createdBy: String? = null
    var updatedBy: String? = null
}