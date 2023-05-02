package org.clevercastle.saas.app.common.vo

import org.clevercastle.saas.model.core.account.User
import java.time.OffsetDateTime

class UserVO {
    companion object {
        fun fromUser(user: User?): UserVO? {
            if (user == null) {
                return null
            }
            return UserVO().apply {
                this.id = user.id
                this.email = user.email
                this.createdAt = user.createdAt
                this.updatedAt = user.updatedAt
                this.createdBy = user.createdBy
                this.updatedBy = user.updatedBy
            }
        }
    }

    lateinit var id: String
    lateinit var email: String
    lateinit var createdAt: OffsetDateTime
    lateinit var updatedAt: OffsetDateTime
    var createdBy: String? = null
    var updatedBy: String? = null
}