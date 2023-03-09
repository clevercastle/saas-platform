package org.clevercastle.saas.core.model.account

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import org.clevercastle.saas.core.model.BaseEntity
import org.clevercastle.saas.core.model.EntityUtil
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.persistence.Entity
import javax.persistence.Id

enum class UserWorkspaceRole {
    Admin,
    Maintain,
    NormalUser
}

@Entity(name = "user_workspace_mapping")
class UserWorkspaceMappingEntity : BaseEntity() {
    companion object : PanacheCompanionBase<UserWorkspaceMappingEntity, String>

    @Id
    var id: String = "${EntityUtil.userWorkspaceMapping}${UUID.randomUUID()}"
    lateinit var userId: String
    lateinit var workspaceId: String
    lateinit var workspaceUserId: String
    lateinit var workspaceUserName: String
    lateinit var role: UserWorkspaceRole
}

@ApplicationScoped
class UserWorkspaceMappingEntityRepository : PanacheRepository<UserWorkspaceMappingEntity> {
    fun listWorkspaces(userId: String): List<UserWorkspaceMappingEntity> {
        return list("user_id", userId)
    }
}