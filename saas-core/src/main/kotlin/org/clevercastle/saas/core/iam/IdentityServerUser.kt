package org.clevercastle.saas.core.iam

sealed class IdentityServerUser

class CognitoUser(val email: String, val userSub: String, val cognitoUserIdentityId: String) : IdentityServerUser()
class Auth0User(val email: String, val userSub: String, val auth0UserIdentityId: String) : IdentityServerUser()

