package org.clevercastle.saas.core.app.alpha.task.bo

import org.clevercastle.saas.model.app.ResourceOwnerType

class CreateAlphaTaskBO {
    lateinit var workspaceId: String

    lateinit var ownerId: String
    lateinit var ownerType: ResourceOwnerType

    lateinit var projectId: String

    lateinit var name: String
    lateinit var taskPath: String

    lateinit var groupId: String
}