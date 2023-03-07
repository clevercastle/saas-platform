package org.clevercastle.saas.core.model.account

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import org.clevercastle.saas.core.model.BaseEntity
import org.clevercastle.saas.core.model.EntityUtil
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.persistence.Entity
import javax.persistence.Id

enum class UserTenantRole {
    Admin,
    Maintain,
    NormalUser
}

@Entity(name = "user_tenant_mapping")
class UserTenantMappingEntity : BaseEntity() {
    companion object : PanacheCompanionBase<UserTenantMappingEntity, String>

    @Id
    var id: String = "${EntityUtil.userTenantMapping}${UUID.randomUUID()}"
    lateinit var userId: String
    lateinit var tenantId: String
    lateinit var tenantUserId: String
    lateinit var tenantUserName: String
    lateinit var role: UserTenantRole
}

@ApplicationScoped
class UserTenantMappingEntityRepository : PanacheRepository<UserTenantMappingEntity>