package org.clevercastle.saas.core.account

import org.clevercastle.saas.core.model.account.UserWorkspaceMappingEntity
import org.clevercastle.saas.core.model.account.UserWorkspaceRole
import org.clevercastle.saas.core.model.account.WorkspaceEntity

class Workspace {
    companion object {
        fun fromWorkspaceEntity(workspaceEntity: WorkspaceEntity): Workspace {
            return Workspace().apply {
                this.id = workspaceEntity.id
                this.name = workspaceEntity.name
            }
        }

        fun fromWorkspaceEntityWithUserWorkspaceMapping(workspaceEntity: WorkspaceEntity,
                                                        userWorkspaceMappings: List<UserWorkspaceMappingEntity>): Workspace {
            return Workspace().apply {
                this.id = workspaceEntity.id
                this.name = workspaceEntity.name
                this.userWorkspaces.putAll(userWorkspaceMappings
                        .associate { it.userId to UserWorkspaceMapping.fromUserWorkspaceMappingEntity(it) })
            }
        }
    }

    lateinit var id: String
    var name: String? = null
    val userWorkspaces = mutableMapOf<String, UserWorkspaceMapping>()
}

class UserWorkspace {
    companion object {
        fun fromUserWorkspaceMappingEntity(mapping: UserWorkspaceMappingEntity, workspaceName: String): UserWorkspace {
            return UserWorkspace().apply {
                this.workspaceName = workspaceName
                this.workspaceId = mapping.workspaceId
                this.workspaceUserId = mapping.workspaceUserId
                this.workspaceUserName = mapping.workspaceUserName
                this.role = mapping.role
            }
        }
    }

    lateinit var workspaceId: String
    lateinit var workspaceName: String
    lateinit var workspaceUserId: String
    lateinit var workspaceUserName: String
    lateinit var role: UserWorkspaceRole
}

class UserWorkspaceMapping {
    companion object {
        fun fromUserWorkspaceMappingEntity(userWorkspaceMappingEntity: UserWorkspaceMappingEntity): UserWorkspaceMapping {
            return UserWorkspaceMapping().apply {
                this.id = userWorkspaceMappingEntity.id
                this.userId = userWorkspaceMappingEntity.userId
                this.workspaceId = userWorkspaceMappingEntity.workspaceId
                this.workspaceUserId = userWorkspaceMappingEntity.workspaceUserId
                this.workspaceUserName = userWorkspaceMappingEntity.workspaceUserName
                this.role = userWorkspaceMappingEntity.role
            }
        }
    }

    lateinit var id: String
    lateinit var userId: String
    lateinit var workspaceId: String
    lateinit var workspaceUserId: String
    lateinit var workspaceUserName: String
    lateinit var role: UserWorkspaceRole
}