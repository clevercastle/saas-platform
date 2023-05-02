package org.clevercastle.saas.entity.core.account

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.clevercastle.saas.base.IdUtil
import org.clevercastle.saas.entity.BaseEntity

@Entity(name = "workspace")
class WorkspaceEntity : BaseEntity() {
    companion object : PanacheCompanionBase<WorkspaceEntity, String>

    @Id
    var id: String = IdUtil.Companion.AccountSystem.genWorkspaceId()
    var name: String? = null
}

@ApplicationScoped
class WorkspaceEntityRepository : PanacheCompanionBase<WorkspaceEntity, String> {
    fun listWorkspaces(ids: List<String>): List<WorkspaceEntity> {
        return list("id in ?1", ids)
    }
}