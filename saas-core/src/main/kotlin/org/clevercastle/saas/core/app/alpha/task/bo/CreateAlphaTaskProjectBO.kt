package org.clevercastle.saas.core.app.alpha.task.bo

import org.clevercastle.saas.model.app.ResourceOwnerType

class CreateAlphaTaskProjectBO {
    lateinit var workspaceId: String

    lateinit var ownerId: String
    lateinit var ownerType: ResourceOwnerType

    lateinit var name: String
    var liked: Boolean = false
}