package org.clevercastle.saas.core.iam

import com.auth0.client.auth.AuthAPI
import com.auth0.exception.APIException
import org.clevercastle.saas.core.jwt.TokenHolder
import org.clevercastle.saas.util.TimeUtils

class IAMServiceAuth0(
        private val auth0Audience: String,
        private val auth0Scope: String,
        private val auth0Connection: String,
        auth0Domain: String,
        auth0ClientId: String,
        auth0ClientSecret: String
) : IAMService {
    private val auth: AuthAPI = AuthAPI(
            auth0Domain,
            auth0ClientId,
            auth0ClientSecret
    )

    override fun registerUser(email: String, password: String, params: Map<String, Any>): IdentityServerUser {
        try {
            val createdUser = auth.signUp(email, password.toCharArray(), "Username-Password-Authentication")
                    .execute()
            return Auth0User(createdUser.email, Auth0User.genUserSub(createdUser.userId), createdUser.userId)
        } catch (e: APIException) {
            when (e.error) {
                "invalid_password" -> {
                    throw RuntimeException("Password is not strong enough")
                }
                "invalid_signup" -> {
                    throw RuntimeException("Fail to create the user")
                }
                else -> {
                    throw e
                }
            }
        }
    }

    override fun login(email: String, password: String): TokenHolder {
        val auth0TokenHolder = auth.login(email, password.toCharArray(), auth0Connection)
                .setScope(auth0Scope)
                .setAudience(auth0Audience).execute()
        return TokenHolder(auth0TokenHolder.accessToken, auth0TokenHolder.idToken, auth0TokenHolder.refreshToken,
                auth0TokenHolder.tokenType, TimeUtils.from(auth0TokenHolder.expiresAt), auth0TokenHolder.scope)
    }

    override fun refresh(refreshToken: String): TokenHolder {
        val auth0TokenHolder = auth.renewAuth(refreshToken).execute()
        return TokenHolder(auth0TokenHolder.accessToken, auth0TokenHolder.idToken, auth0TokenHolder.refreshToken,
                auth0TokenHolder.tokenType, TimeUtils.from(auth0TokenHolder.expiresAt), auth0TokenHolder.scope)
    }

    override fun disableUser(iamId: String) {
        TODO("Not yet implemented")
    }

    override fun enableUser(iamId: String) {
        TODO("Not yet implemented")
    }

    override fun getUser(iamId: String): IdentityServerUser {
        TODO("Not yet implemented")
    }
}