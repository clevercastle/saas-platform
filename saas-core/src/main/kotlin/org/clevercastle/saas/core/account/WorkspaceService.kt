package org.clevercastle.saas.core.account

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.clevercastle.saas.base.IdUtil
import org.clevercastle.saas.base.TimeUtils
import org.clevercastle.saas.core.internal.auth.SecurityService
import org.clevercastle.saas.entity.core.account.*
import org.clevercastle.saas.model.core.account.Account
import org.clevercastle.saas.model.core.account.AccountRole
import org.clevercastle.saas.model.core.account.Workspace

@ApplicationScoped
class WorkspaceService {
    @Inject
    private lateinit var accountEntityRepository: AccountEntityRepository

    @Inject
    private lateinit var workspaceEntityRepository: WorkspaceEntityRepository

    @Inject
    private lateinit var userWorkspaceTeamMappingEntityRepository: UserWorkspaceTeamMappingEntityRepository

    @Inject
    private lateinit var workspaceTeamEntityRepository: WorkspaceTeamEntityRepository

    @Inject
    private lateinit var securityService: SecurityService

    // region workspace
    fun getWorkspace(workspaceId: String): Workspace? {
        return WorkspaceConverter.converter.fromEntity(workspaceEntityRepository.findById(workspaceId))
    }

    @Transactional
    fun createWorkspace(userId: String, workspaceName: String, pAccountName: String): Workspace {
        val workspaceId = IdUtil.Companion.AccountSystem.genWorkspaceId()
        val workspaceEntity = WorkspaceEntity().apply {
            this.id = workspaceId
            this.name = workspaceName
            this.createdAt = TimeUtils.now()
            this.updatedAt = TimeUtils.now()
            this.createdBy = this.id
            this.updatedBy = this.id
        }

        val accountEntity = AccountEntity().apply {
            this.userId = userId
            this.workspaceId = workspaceEntity.id
            this.name = pAccountName
            this.role = AccountRole.Owner
            this.createdAt = TimeUtils.now()
            this.updatedAt = TimeUtils.now()
            this.createdBy = this.id
            this.updatedBy = this.id
        }
        workspaceEntityRepository.persist(workspaceEntity)
        accountEntityRepository.persist(accountEntity)
        return WorkspaceConverter.converter.fromEntity(workspaceEntity)!!
    }

    fun listAccountByUser(userId: String): List<Account> {
        val mappings = accountEntityRepository.listAccount(userId)
        return mappings.map { AccountConverter.converter.fromEntity(it)!! }
    }

    fun listWorkspaces(ids: List<String>): List<Workspace> {
        return workspaceEntityRepository.listWorkspaces(ids).map { WorkspaceConverter.converter.fromEntity(it)!! }
    }

    @Transactional
    fun joinWorkspace(userId: String, workspaceId: String, pAccountName: String, role: AccountRole) {

    }


    // endregion

}