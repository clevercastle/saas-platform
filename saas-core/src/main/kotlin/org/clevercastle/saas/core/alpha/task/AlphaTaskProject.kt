package org.clevercastle.saas.core.alpha.task

import org.clevercastle.saas.base.IdUtil
import org.clevercastle.saas.base.ResourceOwnerType
import org.clevercastle.saas.core.entity.alpha.AlphaTaskProjectEntity

class AlphaTaskProject {
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

    var id: String = IdUtil.Companion.Alpha.genTaskId()

    lateinit var workspaceId: String
    lateinit var name: String

    lateinit var ownerId: String
    lateinit var ownerType: ResourceOwnerType

    var liked: Boolean = false
}