package org.clevercastle.saas.core.model.account

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import org.clevercastle.saas.core.model.BaseEntity
import org.clevercastle.saas.core.model.EntityUtil
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.persistence.AttributeConverter
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.Id

enum class WorkspaceUserRole {
    Admin,
    Maintain,
    NormalUser
}

class WorkspaceUserRoleHibernateConverter : AttributeConverter<WorkspaceUserRole, String> {
    override fun convertToDatabaseColumn(attribute: WorkspaceUserRole?): String {
        return attribute!!.name
    }

    override fun convertToEntityAttribute(dbData: String?): WorkspaceUserRole {
        return WorkspaceUserRole.valueOf(dbData!!)
    }
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

    @field:Convert(converter = WorkspaceUserRoleHibernateConverter::class)
    lateinit var role: WorkspaceUserRole
}

@ApplicationScoped
class UserWorkspaceMappingEntityRepository : PanacheRepository<UserWorkspaceMappingEntity> {
    fun listWorkspaces(userId: String): List<UserWorkspaceMappingEntity> {
        return list("user_id", userId)
    }
}