package org.clevercastle.saas.core.account

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.clevercastle.saas.entity.core.account.AccountDepartmentMappingEntity
import org.clevercastle.saas.entity.core.account.AccountDepartmentMappingEntityRepository
import org.clevercastle.saas.entity.core.account.DepartmentEntity
import org.clevercastle.saas.entity.core.account.DepartmentEntityRepository
import org.clevercastle.saas.model.core.account.Department
import org.clevercastle.saas.model.core.account.DepartmentRole

@ApplicationScoped
class DepartmentService {
    private lateinit var accountDepartmentMappingEntityRepository: AccountDepartmentMappingEntityRepository
    private lateinit var departmentEntityRepository: DepartmentEntityRepository

    constructor()

    @Inject
    constructor(
        accountDepartmentMappingEntityRepository: AccountDepartmentMappingEntityRepository,
        departmentEntityRepository: DepartmentEntityRepository
    ) {
        this.accountDepartmentMappingEntityRepository = accountDepartmentMappingEntityRepository
        this.departmentEntityRepository = departmentEntityRepository
    }

    fun createDepartment(workspaceId: String, departmentName: String): Department {
        val entity = DepartmentEntity()
            .apply {
                this.workspaceId = workspaceId
                this.name = departmentName
            }
        departmentEntityRepository.persist(entity)
        return DepartmentConverter.converter.fromEntity(entity)!!
    }

    fun listDepartment(workspaceId: String): List<Department> {
        return this.departmentEntityRepository.listDepartment(workspaceId)
            .map { DepartmentConverter.converter.fromEntity(it)!! }
    }


    /**
     * need to check the current user department role
     */
    fun addAccount(accountId: String, departmentId: String, role: DepartmentRole) {
        val entity = AccountDepartmentMappingEntity()
            .apply {
                this.accountId = accountId
                this.departmentId = departmentId
                this.role = role
            }
        accountDepartmentMappingEntityRepository.persist(entity)
    }

    /**
     * need to check the current user department role
     */
    fun removeAccount(accountId: String, departmentId: String) {
        TODO()
    }

    fun leaveDepartment() {
        TODO()
    }
}