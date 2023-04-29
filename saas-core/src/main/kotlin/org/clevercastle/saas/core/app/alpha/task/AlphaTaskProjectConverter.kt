package org.clevercastle.saas.core.app.alpha.task

import org.clevercastle.saas.entity.app.alpha.AlphaTaskProjectEntity
import org.clevercastle.saas.model.app.alpha.AlphaTaskProject


class AlphaTaskProjectConverter {
    companion object {
        fun fromEntity(entity: AlphaTaskProjectEntity): AlphaTaskProject {
            return AlphaTaskProject().apply {
                this.id = entity.id
                this.workspaceId = entity.workspaceId
                this.ownerId = entity.ownerId
                this.ownerType = entity.ownerType
                this.name = entity.name
                this.liked = entity.liked
            }
        }
    }
}