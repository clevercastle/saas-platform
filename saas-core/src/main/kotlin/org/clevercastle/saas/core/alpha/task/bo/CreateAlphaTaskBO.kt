package org.clevercastle.saas.core.alpha.task.bo

import org.clevercastle.saas.base.ResourceOwnerType

class CreateAlphaTaskBO {
    lateinit var workspaceId: String

    lateinit var ownerId: String
    lateinit var ownerType: ResourceOwnerType

    lateinit var projectId: String

    lateinit var name: String
    lateinit var taskPath: String

    lateinit var groupId: String
}