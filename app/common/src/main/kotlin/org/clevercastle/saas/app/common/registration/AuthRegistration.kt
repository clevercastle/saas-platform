package org.clevercastle.saas.app.common.registration

import org.clevercastle.saas.core.iam.IAMServiceAuth0
import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces

@ApplicationScoped
class AuthRegistration {
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

    @Produces
    fun iamServiceAuth0(): IAMServiceAuth0 {
        return IAMServiceAuth0(auth0Audience, auth0Scope, auth0Connection, auth0Domain, auth0ClientId, auth0ClientSecret)
    }
}