package org.clevercastle.saas.model.core.account

import org.clevercastle.saas.model.BaseModel

class User : BaseModel() {
    lateinit var id: String
    lateinit var email: String
    val workspaces = mutableListOf<Workspace>()
}