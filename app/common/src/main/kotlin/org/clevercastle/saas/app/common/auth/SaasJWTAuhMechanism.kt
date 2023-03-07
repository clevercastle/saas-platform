package org.clevercastle.saas.app.common.auth

import io.quarkus.security.identity.IdentityProviderManager
import io.quarkus.security.identity.SecurityIdentity
import io.quarkus.security.identity.request.AuthenticationRequest
import io.quarkus.smallrye.jwt.runtime.auth.JWTAuthMechanism
import io.quarkus.smallrye.jwt.runtime.auth.JsonWebTokenCredential
import io.quarkus.vertx.http.runtime.security.ChallengeData
import io.quarkus.vertx.http.runtime.security.HttpAuthenticationMechanism
import io.quarkus.vertx.http.runtime.security.HttpCredentialTransport
import io.quarkus.vertx.http.runtime.security.HttpSecurityUtils
import io.smallrye.mutiny.Uni
import io.vertx.ext.web.RoutingContext
import org.apache.commons.lang3.StringUtils
import org.clevercastle.saas.app.common.auth.authrequest.SaasTokenAuthenticationRequest
import java.util.*
import javax.annotation.Priority
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Alternative
import javax.inject.Inject

@Alternative
@Priority(1)
@ApplicationScoped
class SaasJWTAuhMechanism : HttpAuthenticationMechanism {
    companion object {
        protected val AUTHORIZATION_HEADER = "Authorization"
        protected val BEARER = "Bearer"
        protected val BEARER_SCHEME_PREFIX = "$BEARER "
    }

    @Inject
    private lateinit var jwt: JWTAuthMechanism

    override fun authenticate(context: RoutingContext?, identityProviderManager: IdentityProviderManager?): Uni<SecurityIdentity> {
        val headerAuthorization = context!!.request().headers().get(AUTHORIZATION_HEADER)
        if (headerAuthorization != null && headerAuthorization.startsWith(BEARER_SCHEME_PREFIX)) {
            val jwtToken = context.request().headers().get(AUTHORIZATION_HEADER).substring(BEARER_SCHEME_PREFIX.length)
            if (StringUtils.isNotBlank(jwtToken)) {
                return identityProviderManager!!.authenticate(HttpSecurityUtils.setRoutingContextAttribute(
                        SaasTokenAuthenticationRequest(JsonWebTokenCredential(jwtToken)), context))
            }
        }
        return Uni.createFrom().optional(Optional.empty())
    }

    override fun getChallenge(context: RoutingContext?): Uni<ChallengeData> {
        return jwt.getChallenge(context)
    }

    override fun getCredentialTypes(): Set<Class<out AuthenticationRequest>> {
        return setOf(SaasTokenAuthenticationRequest::class.java)
    }

    override fun getCredentialTransport(context: RoutingContext?): Uni<HttpCredentialTransport> {
        return jwt.getCredentialTransport(context)
    }
}