package org.clevercastle.saas.core.iam

import com.auth0.client.auth.AuthAPI
import com.auth0.exception.APIException
import org.clevercastle.saas.core.jwt.TokenHolder
import org.clevercastle.saas.util.TimeUtils
import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class IAMServiceAuth0 : IAMService {

    @ConfigProperty(name = "saas.iam.auth0.domain")
    private lateinit var auth0Domain: String

    @ConfigProperty(name = "saas.iam.auth0.audience")
    private lateinit var auth0Audience: String

    @ConfigProperty(name = "saas.iam.auth0.clientId")
    private lateinit var auth0ClientId: String

    @ConfigProperty(name = "saas.iam.auth0.clientSecret")
    private lateinit var auth0ClientSecret: String

    @ConfigProperty(name = "saas.iam.auth0.scope", defaultValue = "openid profile email offline_access")
    private lateinit var auth0Scope: String

    @ConfigProperty(name = "saas.iam.auth0.connection", defaultValue = "Username-Password-Authentication")
    private lateinit var auth0Connection: String

    private lateinit var auth: AuthAPI

    @PostConstruct
    fun init() {
        auth = AuthAPI(
                auth0Domain,
                auth0ClientId,
                auth0ClientSecret
        )
    }

    override fun registerUser(email: String, password: String, params: Map<String, Any>): IdentityServerUser? {
        try {
            val createdUser = auth.signUp(email, password.toCharArray(), "Username-Password-Authentication")
                    .execute()
            return Auth0User(createdUser.email, createdUser.userId, createdUser.userId)
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