package org.clevercastle.saas.core.iam

sealed class IdentityServerUser(val userSub: String, val oidcIdentityId: String)

class CognitoUser(val email: String, userSub: String, cognitoUserIdentityId: String) : IdentityServerUser(userSub, cognitoUserIdentityId)
class Auth0User(val email: String, userSub: String, auth0UserIdentityId: String) : IdentityServerUser(userSub, auth0UserIdentityId) {
    companion object {
        fun genUserSub(createdUserId: String): String {
            return "auth0|${createdUserId}"
        }
    }
}

