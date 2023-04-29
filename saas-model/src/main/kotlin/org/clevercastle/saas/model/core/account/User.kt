package org.clevercastle.saas.model.core.account

import java.time.OffsetDateTime

class User {
    lateinit var id: String
    lateinit var email: String
    val workspaces = mutableListOf<Workspace>()
    lateinit var createdAt: OffsetDateTime
    lateinit var updatedAt: OffsetDateTime
    var createdBy: String? = null
    var updatedBy: String? = null
}