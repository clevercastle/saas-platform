package org.clevercastle.saas.entity.core.account

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.clevercastle.saas.base.IdUtil
import org.clevercastle.saas.entity.BaseEntity


@Entity(name = "department")
class DepartmentEntity : BaseEntity() {
    companion object : PanacheCompanionBase<DepartmentEntity, String>

    @Id
    var id: String = IdUtil.Companion.AccountSystem.genAccountId()
    lateinit var workspaceId: String
    lateinit var name: String
    var description: String? = null
}


@ApplicationScoped
class DepartmentEntityRepository : PanacheRepositoryBase<DepartmentEntity, String> {
    fun listDepartment(workspaceId: String): List<DepartmentEntity> {
        return list("workspaceId", workspaceId)
    }
}