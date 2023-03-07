package org.clevercastle.saas.app.common.auth.identityprovider

import io.quarkus.security.identity.AuthenticationRequestContext
import io.quarkus.security.identity.IdentityProvider
import io.quarkus.security.identity.SecurityIdentity
import io.quarkus.security.runtime.QuarkusSecurityIdentity
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.infrastructure.Infrastructure
import io.smallrye.mutiny.unchecked.Unchecked
import org.clevercastle.saas.app.common.auth.AbstractIdentityProvider
import org.clevercastle.saas.app.common.auth.Auth0JWTPayload
import org.clevercastle.saas.app.common.auth.JWTPayload
import org.clevercastle.saas.app.common.auth.SaasPrincipal
import org.clevercastle.saas.app.common.auth.authrequest.SaasTokenAuthenticationRequest
import org.clevercastle.saas.core.account.UserService
import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject


@ApplicationScoped
class SaasAuth0IdentityProvider : IdentityProvider<SaasTokenAuthenticationRequest>, AbstractIdentityProvider() {
    @ConfigProperty(name = "saas.iam.auth0.issuer")
    private lateinit var issuer: String

    @ConfigProperty(name = "quarkus.security.jaxrs.default-roles-allowed")
    private lateinit var defaultRole: String

    @Inject
    private lateinit var userService: UserService

    override fun getRequestType(): Class<SaasTokenAuthenticationRequest> {
        return SaasTokenAuthenticationRequest::class.java
    }

    override fun getJWTPayloadClass(): Class<out JWTPayload> {
        return Auth0JWTPayload::class.java
    }

    override fun authenticate(request: SaasTokenAuthenticationRequest, context: AuthenticationRequestContext): Uni<SecurityIdentity> {
        val jwtToken = request.token
        val jwtPayload = verifyJWTToken(jwtToken.token)
        val userSub: String?
        when (jwtPayload) {
            is Auth0JWTPayload -> {
                userSub = jwtPayload.sub
            }
            else -> TODO()
        }
        return Uni.createFrom().item { userService.getUserIdByUserSub(jwtPayload.sub) }
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .onItem()
                .transform(Unchecked.function { it ->
                    if (it == null) {
                        throw RuntimeException("User not found")
                    }
                    QuarkusSecurityIdentity.builder()
                            .setPrincipal(SaasPrincipal(it, userSub))
                            .addRole(defaultRole)
                            .addCredential(jwtToken)
                            .setAnonymous(false)
                            .build();
                })
    }

    override fun getIssuer(): String {
        return issuer
    }
}