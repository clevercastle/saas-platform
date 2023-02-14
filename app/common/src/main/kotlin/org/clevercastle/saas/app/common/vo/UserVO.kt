package org.clevercastle.saas.app.common.vo

import org.clevercastle.saas.core.account.User

class UserVO {
    companion object {
        val converter: UserVOConverter = UserVOConverter()
    }

    lateinit var id: String
    lateinit var defaultTenantId: String
    lateinit var email: String
}

class UserVOConverter {
    fun fromUser(user: User): UserVO {
        return UserVO().apply {
            this.id = user.id
            this.defaultTenantId = user.defaultTenantId
            this.email = user.email
        }
    }
}