package org.clevercastle.saas.core.account

import org.clevercastle.saas.core.model.account.TenantEntity
import org.clevercastle.saas.core.model.account.UserTenantMappingEntity
import org.clevercastle.saas.core.model.account.UserTenantRole

class Tenant {
    companion object {
        val converter = TenantConverter()
    }

    lateinit var id: String
    var name: String? = null
    val userTenants = mutableMapOf<String, UserTenantMapping>()
}

class UserTenantMapping {
    companion object {
        val converter = UserTenantMappingConverter()
    }

    lateinit var id: String
    lateinit var userId: String
    lateinit var tenantId: String
    lateinit var tenantUserId: String
    lateinit var tenantUserName: String
    lateinit var role: UserTenantRole
}

class TenantConverter {
    fun fromTenantEntity(tenantEntity: TenantEntity): Tenant {
        return Tenant().apply {
            this.id = tenantEntity.id
            this.name = tenantEntity.name
        }
    }

    fun fromTenantEntityWithUserTenantMapping(tenantEntity: TenantEntity,
                                              userTenantMappings: List<UserTenantMappingEntity>): Tenant {
        return Tenant().apply {
            this.id = tenantEntity.id
            this.name = tenantEntity.name
            this.userTenants.putAll(userTenantMappings
                    .associate { it.userId to UserTenantMapping.converter.fromUserTenantMappingEntity(it) })
        }
    }
}

class UserTenantMappingConverter {
    fun fromUserTenantMappingEntity(userTenantMappingEntity: UserTenantMappingEntity): UserTenantMapping {
        return UserTenantMapping().apply {
            this.id = userTenantMappingEntity.id
            this.userId = userTenantMappingEntity.userId
            this.tenantId = userTenantMappingEntity.tenantId
            this.tenantUserId = userTenantMappingEntity.tenantUserId
            this.tenantUserName = userTenantMappingEntity.tenantUserName
            this.role = userTenantMappingEntity.role
        }
    }
}