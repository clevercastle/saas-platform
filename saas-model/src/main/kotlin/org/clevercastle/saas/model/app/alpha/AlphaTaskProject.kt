package org.clevercastle.saas.model.app.alpha

import org.clevercastle.saas.base.IdUtil
import org.clevercastle.saas.model.app.ResourceOwnerType

class AlphaTaskProject {
    var id: String = IdUtil.Companion.Alpha.genTaskId()

    lateinit var workspaceId: String
    lateinit var name: String

    lateinit var ownerId: String
    lateinit var ownerType: ResourceOwnerType

    var liked: Boolean = false
}