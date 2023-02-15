package org.clevercastle.saas.app.common.auth.identityprovider

import io.quarkus.security.identity.AuthenticationRequestContext
import io.quarkus.security.identity.IdentityProvider
import io.quarkus.security.identity.SecurityIdentity
import io.quarkus.security.runtime.QuarkusSecurityIdentity
import io.smallrye.mutiny.Uni
import org.clevercastle.saas.app.common.auth.AbstractIdentityProvider
import org.clevercastle.saas.app.common.auth.Auth0JWTPayload
import org.clevercastle.saas.app.common.auth.JWTPayload
import org.clevercastle.saas.app.common.auth.SaasPrincipal
import org.clevercastle.saas.app.common.auth.authrequest.SaasTokenAuthenticationRequest
import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.enterprise.context.ApplicationScoped


@ApplicationScoped
class SaasAuth0IdentityProvider : IdentityProvider<SaasTokenAuthenticationRequest>, AbstractIdentityProvider() {
    @ConfigProperty(name = "saas.iam.auth0.issuer")
    private lateinit var issuer: String

    @ConfigProperty(name = "quarkus.security.jaxrs.default-roles-allowed")
    private lateinit var defaultRole: String

    override fun getRequestType(): Class<SaasTokenAuthenticationRequest> {
        return SaasTokenAuthenticationRequest::class.java
    }

    override fun getJWTPayloadClass(): Class<out JWTPayload> {
        return Auth0JWTPayload::class.java
    }

    override fun authenticate(request: SaasTokenAuthenticationRequest, context: AuthenticationRequestContext): Uni<SecurityIdentity> {
        val jwtToken = request.token
        val jwtPayload = verifyJWTToken(jwtToken.token)
        val userId: String?
        val userSub: String?
        when (jwtPayload) {
            is Auth0JWTPayload -> {
                userSub = jwtPayload.sub
                userId = ""
            }
            else -> TODO()
        }
        return Uni.createFrom().item(QuarkusSecurityIdentity.builder()
                .setPrincipal(SaasPrincipal(userId, userSub))
                .addRole(defaultRole)
                .addCredential(jwtToken)
                .setAnonymous(false)
                .build())
    }

    override fun getIssuer(): String {
        return issuer
    }
}