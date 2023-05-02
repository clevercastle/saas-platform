package org.clevercastle.saas.core.account

import org.clevercastle.saas.entity.core.account.UserEntity
import org.clevercastle.saas.model.core.account.User
import org.clevercastle.saas.model.core.account.Workspace

class UserConverter {
    companion object {
        fun fromUserEntity(user: UserEntity?): User? {
            if (user == null) {
                return null
            }
            return User().apply {
                this.id = user.id
                this.email = user.email
                this.createdAt = user.createdAt
                this.updatedAt = user.updatedAt
                this.createdBy = user.createdBy
                this.updatedBy = user.updatedBy
            }
        }

        fun fromUserEntityWithWorkspace(
            user: UserEntity,
            workspaces: List<Workspace>
        ): User {
            return User().apply {
                this.id = user.id
                this.email = user.email
                this.workspaces.addAll(workspaces)
                this.createdAt = user.createdAt
                this.updatedAt = user.updatedAt
                this.createdBy = user.createdBy
                this.updatedBy = user.updatedBy
            }
        }
    }
}