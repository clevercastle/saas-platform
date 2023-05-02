package org.clevercastle.saas.model.core.account

import org.clevercastle.saas.model.BaseModel
import org.clevercastle.saas.model.EnumConverter

class Account : BaseModel() {
    lateinit var id: String
    lateinit var userId: String
    lateinit var workspaceId: String
    lateinit var name: String
    lateinit var role: AccountRole
}

class AccountDepartmentMapping : BaseModel() {
    lateinit var id: String
    lateinit var accountId: String
    lateinit var departmentId: String
    lateinit var role: DepartmentRole
}

class AccountTeamMapping : BaseModel() {
    lateinit var id: String
    lateinit var accountId: String
    lateinit var departmentId: String
    lateinit var role: TeamRole
}


enum class AccountRole {
    Owner,
    Admin,
    Maintain,
    NormalUser
}

class AccountRoleConverter : EnumConverter<AccountRole>(AccountRole::class.java)