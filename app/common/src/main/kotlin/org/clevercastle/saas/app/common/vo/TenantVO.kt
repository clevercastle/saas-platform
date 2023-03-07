package org.clevercastle.saas.app.common.vo

import org.clevercastle.saas.core.account.Tenant
import org.clevercastle.saas.core.account.UserTenant
import org.clevercastle.saas.core.model.account.UserTenantRole

class TenantVO {
    companion object {
        fun fromTenant(tenant: Tenant): TenantVO {
            return TenantVO().apply {
                this.id = tenant.id
                this.name = tenant.name
            }
        }
    }

    lateinit var id: String
    var name: String? = null
}

class UserTenantVO {
    companion object {
        fun fromUserTenant(userTenant: UserTenant): UserTenantVO {
            return UserTenantVO().apply {
                this.tenantId = userTenant.tenantId
                this.tenantName = userTenant.tenantName
                this.tenantUserId = userTenant.tenantUserId
                this.tenantUserName = userTenant.tenantUserName
                this.role = userTenant.role
            }
        }
    }

    lateinit var tenantId: String
    lateinit var tenantName: String
    lateinit var tenantUserId: String
    lateinit var tenantUserName: String
    lateinit var role: UserTenantRole
}