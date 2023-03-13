package org.clevercastle.saas.core.alpha.task

import org.clevercastle.saas.core.model.EntityUtil
import org.clevercastle.saas.core.model.ResourceOwnerType
import org.clevercastle.saas.core.model.ResourceOwnerTypeConverter
import org.clevercastle.saas.core.model.alpha.AlphaTaskEntity
import org.clevercastle.saas.core.model.alpha.AlphaTaskStatus
import javax.persistence.Convert

class AlphaTask {
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

    var id: String = EntityUtil.Companion.Alpha.genTaskId()

    lateinit var workspaceId: String

    lateinit var ownerId: String

    @field:Convert(converter = ResourceOwnerTypeConverter::class)
    lateinit var ownerType: ResourceOwnerType

    lateinit var projectId: String

    lateinit var name: String
    lateinit var status: AlphaTaskStatus
    lateinit var taskPath: String

    lateinit var groupId: String
    var version: Int = 0
}