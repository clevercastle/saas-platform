package org.clevercastle.saas.core.entity.alpha

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.clevercastle.saas.base.IdUtil
import org.clevercastle.saas.base.ResourceOwnerType
import org.clevercastle.saas.base.ResourceOwnerTypeConverter
import org.clevercastle.saas.base.alpha.AlphaTaskStatus
import org.clevercastle.saas.base.alpha.AlphaTaskStatusHibernateConverter
import org.clevercastle.saas.core.entity.BaseEntity

@Entity(name = "alpha_task")
class AlphaTaskEntity : BaseEntity() {
    companion object : PanacheCompanionBase<AlphaTaskEntity, String>

    @Id
    var id: String = IdUtil.Companion.Alpha.genTaskId()

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