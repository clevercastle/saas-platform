package org.clevercastle.saas.entity.core.account

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.clevercastle.saas.base.IdUtil
import org.clevercastle.saas.entity.BaseEntity
import org.clevercastle.saas.model.core.account.AccountRole
import org.clevercastle.saas.model.core.account.AccountRoleConverter
import org.clevercastle.saas.model.core.account.DepartmentRole
import org.clevercastle.saas.model.core.account.TeamRole
import java.util.*


@Entity(name = "account")
class AccountEntity : BaseEntity() {
    companion object : PanacheCompanionBase<AccountEntity, String>

    @Id
    var id: String = IdUtil.Companion.AccountSystem.genAccountId()
    lateinit var userId: String
    lateinit var workspaceId: String
    lateinit var name: String

    @field:Convert(converter = AccountRoleConverter::class)
    lateinit var role: AccountRole
}

@Entity(name = "account_department_mapping")
class AccountDepartmentMappingEntity : BaseEntity() {
    @Id
    var id: String = UUID.randomUUID().toString()
    lateinit var accountId: String
    lateinit var departmentId: String
    lateinit var role: DepartmentRole
}

@Entity(name = "account_team_mapping")
class AccountTeamMappingEntity : BaseEntity() {
    @Id
    var id: String = UUID.randomUUID().toString()
    lateinit var accountId: String
    lateinit var teamId: String
    lateinit var role: TeamRole
}


@ApplicationScoped
class AccountEntityRepository : PanacheRepositoryBase<AccountEntity, String> {
    fun listAccount(userId: String): List<AccountEntity> {
        return list("userId", userId)
    }

    fun getByUserIdAndWorkspaceId(userId: String, workspaceId: String): AccountEntity? {
        return find("userId = ?1 and workspaceId= ?2", userId, workspaceId).firstResult()
    }
}

@ApplicationScoped
class AccountDepartmentMappingEntityRepository : PanacheRepositoryBase<AccountDepartmentMappingEntity, String> {
    fun listDepartment(accountId: String): List<AccountDepartmentMappingEntity> {
        return list("accountId", accountId)
    }
}

@ApplicationScoped
class AccountTeamMappingEntityRepository : PanacheRepositoryBase<AccountTeamMappingEntity, String> {
    fun listTeam(accountId: String): List<AccountTeamMappingEntity> {
        return list("account_id", accountId)
    }
}