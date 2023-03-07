package org.clevercastle.saas.core.model.account

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import org.clevercastle.saas.core.model.BaseEntity
import org.clevercastle.saas.core.model.EntityUtil
import javax.enterprise.context.ApplicationScoped
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "tenant")
class TenantEntity : BaseEntity() {
    companion object : PanacheCompanionBase<TenantEntity, String>

    @Id
    var id: String = EntityUtil.genTenantId()
    var name: String? = null
}

@ApplicationScoped
class TenantEntityRepository : PanacheRepository<TenantEntity> {
    fun listTenant(ids: List<String>): List<TenantEntity> {
        return list("id in ?1", ids)
    }
}