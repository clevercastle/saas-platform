package org.clevercastle.saas.core.account

import org.clevercastle.saas.core.model.EntityUtil
import org.clevercastle.saas.core.model.account.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.Transactional

@ApplicationScoped
class TenantService {
    @Inject
    private lateinit var userTenantMappingEntityRepository: UserTenantMappingEntityRepository

    @Inject
    private lateinit var tenantEntityRepository: TenantEntityRepository

    @Transactional
    fun createTenant(userId: String, tenantName: String, tenantUserName: String, isUserDefaultTenant: Boolean) {
        var tenantId = EntityUtil.genTenantId()
        var tenantUserId = EntityUtil.genTenantUserId()
        if (isUserDefaultTenant) {
            tenantId = EntityUtil.genTenantId(EntityUtil.retrieve(userId))
            tenantUserId = EntityUtil.genTenantUserId(EntityUtil.retrieve(userId))
        }

        val tenantEntity = TenantEntity().apply {
            this.id = tenantId
            this.name = tenantName
        }

        val userTenantMappingEntity = UserTenantMappingEntity().apply {
            this.tenantId = tenantEntity.id
            this.userId = userId
            this.tenantUserId = tenantUserId
            this.tenantUserName = tenantUserName
            this.role = UserTenantRole.Admin
        }

        tenantEntityRepository.persist(tenantEntity)
        userTenantMappingEntityRepository.persist(userTenantMappingEntity)
    }
}