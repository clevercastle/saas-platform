package org.clevercastle.saas.core.model.account

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import org.clevercastle.saas.core.model.BaseEntity
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "user_tenant_mapping")
class UserTenantMapping : BaseEntity() {
    companion object : PanacheCompanionBase<UserTenantMapping, String>

    @Id
    @GeneratedValue
    var id: Long? = null
    var userId: Long? = null
    var tenantId: Long? = null
    lateinit var tenant_user_id: String
    lateinit var tenant_user_name: String
}