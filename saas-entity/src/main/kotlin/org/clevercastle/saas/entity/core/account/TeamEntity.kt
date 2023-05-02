package org.clevercastle.saas.entity.core.account

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.clevercastle.saas.base.IdUtil
import org.clevercastle.saas.entity.BaseEntity

@Entity(name = "team")
class TeamEntity : BaseEntity() {
    companion object : PanacheCompanionBase<TeamEntity, String>

    @Id
    var id: String = IdUtil.Companion.AccountSystem.genAccountId()
    lateinit var workspaceId: String
    lateinit var name: String
}


@ApplicationScoped
class TeamEntityRepository : PanacheRepositoryBase<TeamEntity, String> {
    fun listTeam(workspaceId: String): List<TeamEntity> {
        return list("workspaceId", workspaceId)
    }
}