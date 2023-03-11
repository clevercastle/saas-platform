package org.clevercastle.saas.core.account

import org.clevercastle.saas.core.model.account.UserWorkspaceMappingEntity
import org.clevercastle.saas.core.model.account.WorkspaceEntity
import org.clevercastle.saas.core.model.account.WorkspaceTeamEntity
import org.clevercastle.saas.core.model.account.WorkspaceUserRole
import java.time.OffsetDateTime

class Workspace {
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
                        .associate { it.userId to UserWorkspaceMapping.fromEntity(it)!! })
                this.createdAt = workspaceEntity.createdAt
                this.updatedAt = workspaceEntity.updatedAt
                this.createdBy = workspaceEntity.createdBy
                this.updatedBy = workspaceEntity.updatedBy
            }
        }
    }

    lateinit var id: String
    var name: String? = null
    val userWorkspaces = mutableMapOf<String, UserWorkspaceMapping>()
    lateinit var createdAt: OffsetDateTime
    lateinit var updatedAt: OffsetDateTime
    var createdBy: String? = null
    var updatedBy: String? = null
}

class WorkspaceUser {
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

    lateinit var userId: String
    lateinit var workspaceId: String
    lateinit var workspaceName: String
    lateinit var workspaceUserName: String
    lateinit var role: WorkspaceUserRole

    lateinit var createdAt: OffsetDateTime
    lateinit var updatedAt: OffsetDateTime
    var createdBy: String? = null
    var updatedBy: String? = null
}

class UserWorkspaceMapping {
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

    lateinit var id: String
    lateinit var userId: String
    lateinit var workspaceId: String
    lateinit var workspaceUserName: String
    lateinit var role: WorkspaceUserRole

    lateinit var createdAt: OffsetDateTime
    lateinit var updatedAt: OffsetDateTime
    var createdBy: String? = null
    var updatedBy: String? = null
}


class WorkspaceTeam {
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

    lateinit var id: String
    lateinit var workspaceId: String
    lateinit var name: String
    var description: String? = null
    lateinit var createdAt: OffsetDateTime
    lateinit var updatedAt: OffsetDateTime
    var createdBy: String? = null
    var updatedBy: String? = null
}
