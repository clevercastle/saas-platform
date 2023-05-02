package org.clevercastle.saas.core.account

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.clevercastle.saas.base.TimeUtils
import org.clevercastle.saas.core.internal.auth.SecurityService
import org.clevercastle.saas.core.internal.exception.NotFoundException
import org.clevercastle.saas.entity.core.account.AccountEntity
import org.clevercastle.saas.entity.core.account.AccountEntityRepository
import org.clevercastle.saas.model.core.account.Account
import org.clevercastle.saas.model.core.account.AccountRole

@ApplicationScoped
class AccountService {
    private lateinit var accountEntityRepository: AccountEntityRepository
    private lateinit var securityService: SecurityService

    constructor()

    @Inject
    constructor(accountEntityRepository: AccountEntityRepository, securityService: SecurityService) {
        this.accountEntityRepository = accountEntityRepository
        this.securityService = securityService
    }

    fun getByUserAndWorkspace(userId: String, workspaceId: String): Account? {
        return AccountConverter.converter.fromEntity(
            accountEntityRepository.getByUserIdAndWorkspaceId(
                userId,
                workspaceId
            )
        )
    }

    @Transactional
    fun createAccount(userId: String, workspaceId: String, pAccountName: String, role: AccountRole): Account {
        val accountEntity = AccountEntity().apply {
            this.userId = userId
            this.workspaceId = workspaceId
            this.name = pAccountName
            this.role = role
            this.createdAt = TimeUtils.now()
            this.updatedAt = TimeUtils.now()
            this.createdBy = securityService.getUserId()
            this.updatedBy = securityService.getUserId()
        }
        accountEntityRepository.persist(accountEntity)
        return AccountConverter.converter.fromEntity(accountEntity)!!
    }


    @Transactional
    fun updateAccount(accountId: String, pAccountName: String?, role: AccountRole?) {
        val accountEntity = accountEntityRepository.findById(accountId)
            ?: throw NotFoundException("Account does not exist")
        if (pAccountName != null) {
            accountEntity.name = pAccountName
        }
        if (role != null) {
            accountEntity.role = role
        }
        accountEntity.updatedAt = TimeUtils.now()
        accountEntity.updatedBy = securityService.getUserId()
        accountEntityRepository.persist(accountEntity)
    }

}