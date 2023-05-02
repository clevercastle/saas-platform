package org.clevercastle.saas.model.core.account

import org.clevercastle.saas.model.EnumConverter

enum class OidcProvider {
    Auth0,
    Cognito
}

class OidcProviderHibernateConverter : EnumConverter<OidcProvider>(OidcProvider::class.java)


enum class AccountInWorkspaceRole {
    Admin,
    NormalUser,
    ReadOnlyUser,
}

class AccountInWorkspaceRoleConverter :
    EnumConverter<AccountInWorkspaceRole>(AccountInWorkspaceRole::class.java)