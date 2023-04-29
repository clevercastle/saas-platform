package org.clevercastle.saas.model.core.account

import java.time.OffsetDateTime

class Workspace {
    lateinit var id: String
    var name: String? = null
    val userWorkspaces = mutableMapOf<String, UserWorkspaceMapping>()
    lateinit var createdAt: OffsetDateTime
    lateinit var updatedAt: OffsetDateTime
    var createdBy: String? = null
    var updatedBy: String? = null
}

class WorkspaceUser {
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
    lateinit var id: String
    lateinit var workspaceId: String
    lateinit var name: String
    var description: String? = null
    lateinit var createdAt: OffsetDateTime
    lateinit var updatedAt: OffsetDateTime
    var createdBy: String? = null
    var updatedBy: String? = null
}


class UserWorkspaceTeam {
    lateinit var userId: String
    lateinit var workspaceId: String
    lateinit var name: String
    var description: String? = null
    lateinit var role: UserInWorkspaceTeamRole
}