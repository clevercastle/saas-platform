package org.clevercastle.saas.model.core.account

import org.clevercastle.saas.model.BaseModel
import org.clevercastle.saas.model.EnumConverter


class Team : BaseModel() {
    lateinit var id: String
    lateinit var workspaceId: String
    lateinit var name: String
}


enum class TeamRole {
    Owner,
    Admin,
    Maintain,
    NormalUser
}

class TeamRoleConverter : EnumConverter<TeamRole>(TeamRole::class.java)
