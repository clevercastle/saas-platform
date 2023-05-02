package org.clevercastle.saas.entity.core.account

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.clevercastle.saas.base.IdUtil
import org.clevercastle.saas.entity.BaseEntity
import org.clevercastle.saas.model.core.account.AccountInWorkspaceRole
import org.clevercastle.saas.model.core.account.AccountInWorkspaceRoleConverter


@Entity(name = "workspace_team")
class WorkspaceTeamEntity : BaseEntity() {
    companion object : PanacheCompanionBase<WorkspaceTeamEntity, String>

    @Id
    var id: String = IdUtil.Companion.AccountSystem.genWorkspaceTeamId()
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
    var id: String = IdUtil.Companion.AccountSystem.genWorkspaceUserTeamMappingId()

    lateinit var userId: String
    lateinit var workspaceId: String
    lateinit var workspaceTeamId: String

    @field:Convert(converter = AccountInWorkspaceRoleConverter::class)
    lateinit var accountInWorkspaceRole: AccountInWorkspaceRole
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

