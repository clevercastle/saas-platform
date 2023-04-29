package org.clevercastle.saas.core.account

import org.clevercastle.saas.entity.core.account.UserWorkspaceMappingEntity
import org.clevercastle.saas.entity.core.account.UserWorkspaceTeamMappingEntity
import org.clevercastle.saas.entity.core.account.WorkspaceEntity
import org.clevercastle.saas.entity.core.account.WorkspaceTeamEntity
import org.clevercastle.saas.model.core.account.*

class WorkspaceConverter {

    companion object {
        fun fromWorkspaceEntity(workspaceEntity: WorkspaceEntity): Workspace {
            return Workspace().apply {
                this.id = workspaceEntity.id
                this.name = workspaceEntity.name
                this.createdAt = workspaceEntity.createdAt
                this.updatedAt = workspaceEntity.updatedAt
                this.createdBy = workspaceEntity.createdBy
                this.updatedBy = workspaceEntity.updatedBy
            }
        }

        fun fromWorkspaceEntityWithUserWorkspaceMapping(workspaceEntity: WorkspaceEntity,
                                                        userWorkspaceMappings: List<UserWorkspaceMappingEntity>): Workspace {
            return Workspace().apply {
                this.id = workspaceEntity.id
                this.name = workspaceEntity.name
                this.userWorkspaces.putAll(userWorkspaceMappings
                        .associate { it.userId to UserWorkspaceMappingConverter.fromEntity(it)!! })
                this.createdAt = workspaceEntity.createdAt
                this.updatedAt = workspaceEntity.updatedAt
                this.createdBy = workspaceEntity.createdBy
                this.updatedBy = workspaceEntity.updatedBy
            }
        }
    }
}

class WorkspaceUserConverter {
    companion object {
        fun fromEntity(mapping: UserWorkspaceMappingEntity, workspaceName: String): WorkspaceUser {
            return WorkspaceUser().apply {
                this.userId = mapping.userId
                this.workspaceId = mapping.workspaceId
                this.workspaceName = workspaceName
                this.workspaceUserName = mapping.workspaceUserName
                this.role = mapping.role
                this.createdAt = mapping.createdAt
                this.updatedAt = mapping.updatedAt
                this.createdBy = mapping.createdBy
                this.updatedBy = mapping.updatedBy
            }
        }
    }
}

class WorkspaceTeamConverter {
    companion object {
        fun fromEntity(workspaceTeamEntity: WorkspaceTeamEntity): WorkspaceTeam {
            return WorkspaceTeam().apply {
                this.id = workspaceTeamEntity.id
                this.workspaceId = workspaceTeamEntity.workspaceId
                this.name = workspaceTeamEntity.name
                this.description = workspaceTeamEntity.description
                this.createdAt = workspaceTeamEntity.createdAt
                this.updatedAt = workspaceTeamEntity.updatedAt
                this.createdBy = workspaceTeamEntity.createdBy
                this.updatedBy = workspaceTeamEntity.updatedBy
            }
        }
    }
}

class UserWorkspaceMappingConverter {
    companion object {
        fun fromEntity(userWorkspaceMappingEntity: UserWorkspaceMappingEntity?): UserWorkspaceMapping? {
            if (userWorkspaceMappingEntity == null) {
                return null
            }
            return UserWorkspaceMapping().apply {
                this.id = userWorkspaceMappingEntity.id
                this.userId = userWorkspaceMappingEntity.userId
                this.workspaceId = userWorkspaceMappingEntity.workspaceId
                this.workspaceUserName = userWorkspaceMappingEntity.workspaceUserName
                this.role = userWorkspaceMappingEntity.role

                this.createdAt = userWorkspaceMappingEntity.createdAt
                this.updatedAt = userWorkspaceMappingEntity.updatedAt
                this.createdBy = userWorkspaceMappingEntity.createdBy
                this.updatedBy = userWorkspaceMappingEntity.updatedBy
            }
        }
    }
}

class UserWorkspaceTeamConverter {
    companion object {
        fun fromEntity(workspaceTeamEntity: WorkspaceTeamEntity, userWorkspaceTeamMappingEntity: UserWorkspaceTeamMappingEntity): UserWorkspaceTeam {
            return UserWorkspaceTeam().apply {
                this.userId = userWorkspaceTeamMappingEntity.userId
                this.workspaceId = workspaceTeamEntity.workspaceId
                this.name = workspaceTeamEntity.name
                this.description = workspaceTeamEntity.description
                this.role = userWorkspaceTeamMappingEntity.userInWorkspaceTeamRole
            }
        }
    }
}