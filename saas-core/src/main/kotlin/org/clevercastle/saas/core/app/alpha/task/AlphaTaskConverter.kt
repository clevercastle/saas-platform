package org.clevercastle.saas.core.app.alpha.task

import org.clevercastle.saas.entity.app.alpha.AlphaTaskEntity
import org.clevercastle.saas.model.app.alpha.AlphaTask


class AlphaTaskConverter {
    companion object {
        fun fromEntity(entity: AlphaTaskEntity?): AlphaTask? {
            if (entity == null) {
                return null
            }
            return AlphaTask().apply {
                this.id = entity.id
                this.workspaceId = entity.workspaceId
                this.ownerId = entity.ownerId
                this.ownerType = entity.ownerType
                this.projectId = entity.projectId
                this.name = entity.name
                this.status = entity.status
                this.taskPath = entity.taskPath
                this.groupId = entity.groupId
                this.version = entity.version
            }
        }
    }
}