package org.clevercastle.saas.core.model.account

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.clevercastle.saas.base.IdUtil
import org.clevercastle.saas.base.account.WorkspaceUserRole
import org.clevercastle.saas.base.account.WorkspaceUserRoleHibernateConverter
import org.clevercastle.saas.core.model.BaseEntity
import java.util.*


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