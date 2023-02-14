package org.clevercastle.saas.core.model.account

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import org.clevercastle.saas.core.model.BaseEntity
import org.clevercastle.saas.core.model.EntityPrefix
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "tenant")
class TenantEntity : BaseEntity() {
    companion object : PanacheCompanionBase<TenantEntity, String>

    @Id
    var id: String = "${EntityPrefix.tenant}${UUID.randomUUID()}"
    var name: String? = null
}

@ApplicationScoped
class TenantEntityRepository : PanacheRepository<TenantEntity>