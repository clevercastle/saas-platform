package org.clevercastle.saas.app.common.auth

import io.quarkus.security.identity.SecurityIdentity
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class SecurityService {
    @Inject
    private lateinit var securityIdentity: SecurityIdentity

    fun getUserId(): String {
        return securityIdentity.principal.name
    }
}