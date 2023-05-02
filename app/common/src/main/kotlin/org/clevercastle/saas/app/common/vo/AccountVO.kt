package org.clevercastle.saas.app.common.vo

import io.quarkus.runtime.annotations.RegisterForReflection
import org.clevercastle.saas.model.core.account.Account
import org.clevercastle.saas.model.core.account.AccountRole
import org.clevercastle.saas.model.core.account.Workspace

@RegisterForReflection
class AccountVO {
    lateinit var accountId: String
    lateinit var userId: String
    lateinit var workspaceId: String
    lateinit var workspaceName: String
    lateinit var accountName: String
    lateinit var role: AccountRole
}

class AccountVOConverter {
    companion object {
        fun fromAccountAndWorkspace(account: Account, workspace: Workspace): AccountVO {
            return AccountVO().apply {
                this.accountId = account.id
                this.userId = account.userId
                this.workspaceId = account.workspaceId
                this.workspaceName = workspace.name
                this.accountName = account.name
                this.role = account.role
            }
        }
    }
}
