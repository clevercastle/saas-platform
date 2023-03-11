package org.clevercastle.saas.core.account

import org.clevercastle.saas.core.model.account.UserEntity
import java.time.OffsetDateTime

class User {
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

        fun fromUserEntityWithWorkspace(user: UserEntity,
                                        workspaces: List<Workspace>): User {
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

    lateinit var id: String
    lateinit var email: String
    val workspaces = mutableListOf<Workspace>()
    lateinit var createdAt: OffsetDateTime
    lateinit var updatedAt: OffsetDateTime
    var createdBy: String? = null
    var updatedBy: String? = null
}