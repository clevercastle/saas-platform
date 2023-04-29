package org.clevercastle.saas.model.app.alpha

import org.clevercastle.saas.base.IdUtil
import org.clevercastle.saas.model.app.ResourceOwnerType

class AlphaTask {
    var id: String = IdUtil.Companion.Alpha.genTaskId()
    lateinit var workspaceId: String
    lateinit var ownerId: String
    lateinit var ownerType: ResourceOwnerType
    lateinit var projectId: String
    lateinit var name: String
    lateinit var status: AlphaTaskStatus
    lateinit var taskPath: String
    lateinit var groupId: String
    var version: Int = 0
}