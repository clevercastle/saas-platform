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
                this.defaultTenantId = user.defaultTenantId
                this.email = user.email
            }
        }

        fun fromUserEntityWithTenants(user: UserEntity,
                                      tenants: List<Tenant>): User {
            return User().apply {
                this.id = user.id
                this.defaultTenantId = user.defaultTenantId
                this.email = user.email
                this.tenants.addAll(tenants)
            }
        }
    }

    lateinit var id: String
    lateinit var defaultTenantId: String
    lateinit var email: String
    val tenants = mutableListOf<Tenant>()
}