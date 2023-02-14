package org.clevercastle.saas.core.account

import org.clevercastle.saas.core.model.account.UserEntity

class User {
    companion object {
        val converter = UserConverter()
    }

    lateinit var id: String
    lateinit var defaultTenantId: String
    lateinit var email: String
}

class UserConverter {
    fun fromUserEntity(user: UserEntity): User {
        return User().apply {
            this.id = user.id
            this.defaultTenantId = user.defaultTenantId
            this.email = user.email
        }
    }
}