package org.clevercastle.saas.core.internal.auth

import io.quarkus.security.identity.SecurityIdentity
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.core.Response
import org.clevercastle.saas.core.account.AccountService
import org.clevercastle.saas.core.internal.exception.HttpResponseException
import org.clevercastle.saas.model.core.account.Account

@ApplicationScoped
class SecurityService {
    @Inject
    private lateinit var securityIdentity: SecurityIdentity

    @Inject
    private lateinit var accountService: AccountService

    fun getUserId(): String {
        return securityIdentity.principal.name
    }

    fun getAccount(): Account {
        val workspaceId = (securityIdentity.principal as SaasPrincipal).workspaceId
        if (workspaceId != null) {
            return accountService.getByUserAndWorkspace(getUserId(), workspaceId)
                ?: throw HttpResponseException(
                    Response.Status.FORBIDDEN.statusCode,
                    "User is not a member of the workspace"
                )
        }
        throw HttpResponseException(
            Response.Status.FORBIDDEN.statusCode,
            "Api needs to be called in the context of a workspace"
        )
    }
}