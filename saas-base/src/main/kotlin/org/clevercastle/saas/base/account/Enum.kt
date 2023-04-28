package org.clevercastle.saas.base.account

import jakarta.persistence.AttributeConverter
import org.clevercastle.saas.base.EnumConverter

enum class OidcProvider {
    Auth0,
    Cognito
}

class OidcProviderHibernateConverter : EnumConverter<OidcProvider>(OidcProvider::class.java)


enum class WorkspaceUserRole {
    Admin,
    Maintain,
    NormalUser
}

class WorkspaceUserRoleHibernateConverter : AttributeConverter<WorkspaceUserRole, String> {
    override fun convertToDatabaseColumn(attribute: WorkspaceUserRole?): String {
        return attribute!!.name
    }

    override fun convertToEntityAttribute(dbData: String?): WorkspaceUserRole {
        return WorkspaceUserRole.valueOf(dbData!!)
    }
}

enum class UserInWorkspaceTeamRole {
    Admin,
    NormalUser,
    ReadOnlyUser,
}

class UserInWorkspaceTeamRoleHibernateConverter : EnumConverter<UserInWorkspaceTeamRole>(UserInWorkspaceTeamRole::class.java)