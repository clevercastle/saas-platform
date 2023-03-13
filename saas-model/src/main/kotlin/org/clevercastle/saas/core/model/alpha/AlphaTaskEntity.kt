package org.clevercastle.saas.core.model.alpha

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import org.clevercastle.saas.core.model.BaseEntity
import org.clevercastle.saas.core.model.EntityUtil
import org.clevercastle.saas.core.model.ResourceOwnerType
import org.clevercastle.saas.core.model.ResourceOwnerTypeConverter
import javax.enterprise.context.ApplicationScoped
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "alpha_task")
class AlphaTaskEntity : BaseEntity() {
    companion object : PanacheCompanionBase<AlphaTaskEntity, String>

    @Id
    var id: String = EntityUtil.Companion.Alpha.genTaskId()

    lateinit var workspaceId: String

    lateinit var ownerId: String

    @field:Convert(converter = ResourceOwnerTypeConverter::class)
    lateinit var ownerType: ResourceOwnerType

    lateinit var projectId: String

    lateinit var name: String

    @field:Convert(converter = AlphaTaskStatusHibernateConverter::class)
    lateinit var status: AlphaTaskStatus
    lateinit var taskPath: String

    lateinit var groupId: String
    var version: Int = 0
}


@ApplicationScoped
class AlphaTaskEntityRepository : PanacheRepositoryBase<AlphaTaskEntity, String> {
    fun listByOwner(ownerId: String): List<AlphaTaskEntity> {
        return find("ownerId = ?1 ", ownerId).list()
    }

    fun listByOwnerAndProject(ownerId: String, projectId: String): List<AlphaTaskEntity> {
        return find("ownerId = ?1 and projectId = ?2", ownerId, projectId).list()
    }

    fun getByTaskId(taskId: String): AlphaTaskEntity? {
        return find("id = ?1", taskId).firstResult()
    }

    fun listByGroupId(groupId: String): List<AlphaTaskEntity> {
        return list("groupId", groupId)
    }
}