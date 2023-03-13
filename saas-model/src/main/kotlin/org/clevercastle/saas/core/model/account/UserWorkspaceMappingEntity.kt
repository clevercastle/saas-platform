package org.clevercastle.saas.core.model.account

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import org.clevercastle.saas.base.IdUtil
import org.clevercastle.saas.base.account.WorkspaceUserRole
import org.clevercastle.saas.base.account.WorkspaceUserRoleHibernateConverter
import org.clevercastle.saas.core.model.BaseEntity
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.Id


@Entity(name = "user_workspace_mapping")
class UserWorkspaceMappingEntity : BaseEntity() {
    companion object : PanacheCompanionBase<UserWorkspaceMappingEntity, String>

    @Id
    var id: String = "${IdUtil.Companion.Account.userWorkspaceMapping}${UUID.randomUUID()}"
    lateinit var userId: String
    lateinit var workspaceId: String
    lateinit var workspaceUserName: String

    @field:Convert(converter = WorkspaceUserRoleHibernateConverter::class)
    lateinit var role: WorkspaceUserRole
}

@ApplicationScoped
class UserWorkspaceMappingEntityRepository : PanacheRepositoryBase<UserWorkspaceMappingEntity, String> {
    fun listWorkspaces(userId: String): List<UserWorkspaceMappingEntity> {
        return list("user_id", userId)
    }

    fun getByUserIdAndWorkspaceId(userId: String, workspaceId: String): UserWorkspaceMappingEntity? {
        return find("user_id = ?1 and workspace_id = ?2", userId, workspaceId).firstResult()
    }
}