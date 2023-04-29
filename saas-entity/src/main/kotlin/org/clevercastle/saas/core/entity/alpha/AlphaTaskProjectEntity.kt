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
import org.clevercastle.saas.core.entity.BaseEntity

@Entity(name = "alpha_task_project")
class AlphaTaskProjectEntity : BaseEntity() {
    companion object : PanacheCompanionBase<AlphaTaskProjectEntity, String>

    @Id
    var id: String = IdUtil.Companion.Alpha.genTaskId()

    lateinit var workspaceId: String
    lateinit var name: String

    lateinit var ownerId: String

    @field:Convert(converter = ResourceOwnerTypeConverter::class)
    lateinit var ownerType: ResourceOwnerType

    var liked: Boolean = false
}

@ApplicationScoped
class AlphaTaskProjectEntityRepository : PanacheRepositoryBase<AlphaTaskProjectEntity, String> {
    fun listByOwner(ownerId: String): List<AlphaTaskProjectEntity> {
        return find("ownerId = ?1 ", ownerId).list()
    }

}
