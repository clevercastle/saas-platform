package org.clevercastle.saas.core.model.account

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import org.clevercastle.saas.core.model.BaseEntity
import org.clevercastle.saas.core.model.EntityUtil
import javax.enterprise.context.ApplicationScoped
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "workspace")
class WorkspaceEntity : BaseEntity() {
    companion object : PanacheCompanionBase<WorkspaceEntity, String>

    @Id
    var id: String = EntityUtil.Companion.Account.genWorkspaceId()
    var name: String? = null
}

@ApplicationScoped
class WorkspaceEntityRepository : PanacheRepository<WorkspaceEntity> {
    fun listWorkspaces(ids: List<String>): List<WorkspaceEntity> {
        return list("id in ?1", ids)
    }
}