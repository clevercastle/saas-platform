package org.clevercastle.saas.core.model.account

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import org.clevercastle.saas.base.IdUtil
import org.clevercastle.saas.base.account.UserInWorkspaceTeamRole
import org.clevercastle.saas.base.account.UserInWorkspaceTeamRoleHibernateConverter
import org.clevercastle.saas.core.model.BaseEntity
import javax.enterprise.context.ApplicationScoped
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.Id


@Entity(name = "workspace_team")
class WorkspaceTeamEntity : BaseEntity() {
    companion object : PanacheCompanionBase<WorkspaceTeamEntity, String>

    @Id
    var id: String = IdUtil.Companion.Account.genWorkspaceTeamId()
    lateinit var workspaceId: String
    lateinit var name: String
    var description: String? = null
}

@ApplicationScoped
class WorkspaceTeamEntityRepository : PanacheRepositoryBase<WorkspaceTeamEntity, String> {
    fun listWorkspaceTeams(workspaceIds: List<String>): List<WorkspaceTeamEntity> {
        return list("id in ?1", workspaceIds)
    }
}


@Entity(name = "user_workspace_team_mapping")
class UserWorkspaceTeamMappingEntity : BaseEntity() {
    companion object : PanacheCompanionBase<UserWorkspaceTeamMappingEntity, String>

    @Id
    var id: String = IdUtil.Companion.Account.genWorkspaceUserTeamMappingId()

    lateinit var userId: String
    lateinit var workspaceId: String
    lateinit var workspaceTeamId: String

    @field:Convert(converter = UserInWorkspaceTeamRoleHibernateConverter::class)
    lateinit var userInWorkspaceTeamRole: UserInWorkspaceTeamRole
}

@ApplicationScoped
class UserWorkspaceTeamMappingEntityRepository : PanacheRepositoryBase<UserWorkspaceTeamMappingEntity, String> {
    fun listUserWorkspaceTeamMappings(userId: String, workspaceId: String): List<UserWorkspaceTeamMappingEntity> {
        return list("userId=?1 and workspaceId=?2", userId, workspaceId)
    }

    fun delete(userId: String, workspaceId: String, teamId: String): Long {
        return delete("userId=?1 and workspaceId=?2 and workspaceTeamId=?3", userId, workspaceId, teamId)
    }
}

