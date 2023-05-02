package org.clevercastle.saas.app.common.vo

import org.clevercastle.saas.model.core.account.Workspace
import java.time.OffsetDateTime

class WorkspaceVO {
    companion object {
        fun fromWorkspace(workspace: Workspace): WorkspaceVO {
            return WorkspaceVO().apply {
                this.id = workspace.id
                this.name = workspace.name
                this.createdAt = workspace.createdAt
                this.updatedAt = workspace.updatedAt
                this.createdBy = workspace.createdBy
                this.updatedBy = workspace.updatedBy
            }
        }
    }

    lateinit var id: String
    var name: String? = null
    lateinit var createdAt: OffsetDateTime
    lateinit var updatedAt: OffsetDateTime
    var createdBy: String? = null
    var updatedBy: String? = null
}

