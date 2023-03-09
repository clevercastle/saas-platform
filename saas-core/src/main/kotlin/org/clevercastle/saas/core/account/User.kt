package org.clevercastle.saas.core.account

import org.clevercastle.saas.core.model.account.UserEntity

class User {
    companion object {
        fun fromUserEntity(user: UserEntity?): User? {
            if (user == null) {
                return null
            }
            return User().apply {
                this.id = user.id
                this.defaultWorkspaceId = user.defaultWorkspaceId
                this.email = user.email
            }
        }

        fun fromUserEntityWithWorkspace(user: UserEntity,
                                        workspaces: List<Workspace>): User {
            return User().apply {
                this.id = user.id
                this.defaultWorkspaceId = user.defaultWorkspaceId
                this.email = user.email
                this.workspaces.addAll(workspaces)
            }
        }
    }

    lateinit var id: String
    lateinit var defaultWorkspaceId: String
    lateinit var email: String
    val workspaces = mutableListOf<Workspace>()
}