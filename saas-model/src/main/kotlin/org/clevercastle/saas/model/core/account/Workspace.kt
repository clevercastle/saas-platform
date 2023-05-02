package org.clevercastle.saas.model.core.account

import org.clevercastle.saas.model.BaseModel

class Workspace : BaseModel() {
    lateinit var id: String
    lateinit var name: String
    var accounts = mutableListOf<Account>()
}

class AccountGroup : BaseModel() {
    lateinit var id: String
    lateinit var workspaceId: String
    lateinit var name: String
    var description: String? = null
}


class AccountWorkspaceMapping : BaseModel() {
    lateinit var accountId: String
    lateinit var workspaceId: String
    lateinit var name: String
    var description: String? = null
    lateinit var role: AccountInWorkspaceRole
}