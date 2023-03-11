package org.clevercastle.saas.core.model.account

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import org.clevercastle.saas.core.model.BaseEntity
import org.clevercastle.saas.core.model.EntityUtil
import javax.enterprise.context.ApplicationScoped
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.Id


@Entity(name = "workspace_team")
class WorkspaceTeamEntity : BaseEntity() {
    companion object : PanacheCompanionBase<WorkspaceTeamEntity, String>

    @Id
    var id: String = EntityUtil.Companion.Account.genWorkspaceTeamId()
    lateinit var workspaceId: String
    lateinit var name: String
    var description: String? = null
}

@ApplicationScoped
class WorkspaceTeamEntityRepository : PanacheRepository<WorkspaceTeamEntity> {
    fun listWorkspaceTeams(workspaceIds: List<String>): List<WorkspaceTeamEntity> {
        return list("id in ?1", workspaceIds)
    }
}


@Entity(name = "user_workspace_team_mapping")
class UserWorkspaceTeamMappingEntity : BaseEntity() {
    companion object : PanacheCompanionBase<UserWorkspaceTeamMappingEntity, String>

    @Id
    var id: String = EntityUtil.Companion.Account.genWorkspaceUserTeamMappingId()

    lateinit var userId: String
    lateinit var workspaceId: String
    lateinit var workspaceTeamId: String

    @field:Convert(converter = UserInWorkspaceTeamRoleHibernateConverter::class)
    lateinit var userInWorkspaceTeamRole: UserInWorkspaceTeamRole
}

@ApplicationScoped
class UserWorkspaceTeamMappingEntityRepository : PanacheRepository<UserWorkspaceTeamMappingEntity> {
    fun listUserWorkspaceTeamMappings(userId: String, workspaceId: String): List<UserWorkspaceTeamMappingEntity> {
        return list("userId=?1 and workspaceId=?2", userId, workspaceId)
    }
}

