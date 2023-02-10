package org.clevercastle.saas.core.model.account

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import org.clevercastle.saas.core.model.BaseEntity
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "tenant")
class TenantEntity : BaseEntity() {
    companion object : PanacheCompanionBase<TenantEntity, String>

    @Id
    var id: String = "user_${UUID.randomUUID()}"
    var name: String? = null
}