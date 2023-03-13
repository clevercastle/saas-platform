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

@Entity(name = "alpha_task_project")
class AlphaTaskProjectEntity : BaseEntity() {
    companion object : PanacheCompanionBase<AlphaTaskProjectEntity, String>

    @Id
    var id: String = EntityUtil.Companion.Alpha.genTaskId()

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
