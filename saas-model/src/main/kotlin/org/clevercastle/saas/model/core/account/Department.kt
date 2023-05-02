package org.clevercastle.saas.model.core.account

import org.clevercastle.saas.model.BaseModel
import org.clevercastle.saas.model.EnumConverter

class Department : BaseModel() {
    lateinit var id: String
    lateinit var workspaceId: String
    lateinit var name: String
}


enum class DepartmentRole {
    Owner,
    Admin,
    Maintain,
    NormalUser
}

class DepartmentRoleConverter : EnumConverter<DepartmentRole>(DepartmentRole::class.java)



