package org.clevercastle.saas.core.alpha.task.bo

import org.clevercastle.saas.base.ResourceOwnerType

class CreateAlphaTaskProjectBO {
    lateinit var workspaceId: String

    lateinit var ownerId: String
    lateinit var ownerType: ResourceOwnerType

    lateinit var name: String
    var liked: Boolean = false
}