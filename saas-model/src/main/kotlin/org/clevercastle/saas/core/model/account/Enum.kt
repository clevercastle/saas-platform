package org.clevercastle.saas.core.model.account

import javax.persistence.AttributeConverter

enum class OidcProvider {
    Auth0,
    Cognito
}

class OidcProviderHibernateConverter : EnumConverter<OidcProvider>(OidcProvider::class.java)

//class OidcProviderHibernateConverter : AttributeConverter<OidcProvider, String> {
//    override fun convertToDatabaseColumn(attribute: OidcProvider?): String {
//        return attribute!!.name
//    }
//
//    override fun convertToEntityAttribute(dbData: String?): OidcProvider {
//        return OidcProvider.valueOf(dbData!!)
//    }
//}

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


open class EnumConverter<T : Enum<*>>(private val clazz: Class<T>) : AttributeConverter<T, String?> {

    override fun convertToDatabaseColumn(attribute: T?): String? {
        if (attribute == null) {
            return null
        }
        return attribute.name
    }

    override fun convertToEntityAttribute(dbData: String?): T? {
        val enums: Array<T> = clazz.enumConstants
        for (e: T in enums) {
            if (e.name.equals(dbData, ignoreCase = false)) {
                return e
            }
        }
        return null
    }

}
