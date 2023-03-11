package org.clevercastle.saas.core.internal.auth

import io.quarkus.security.identity.SecurityIdentity
import org.clevercastle.saas.core.account.UserWorkspaceMapping
import org.clevercastle.saas.core.account.WorkspaceService
import org.clevercastle.saas.core.internal.exception.HttpResponseException
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.core.Response

@ApplicationScoped
class SecurityService {
    @Inject
    private lateinit var securityIdentity: SecurityIdentity

    @Inject
    private lateinit var workspaceService: WorkspaceService

    fun getUserId(): String {
        return securityIdentity.principal.name
    }

    fun getUserWorkspaceMapping(): UserWorkspaceMapping {
        val workspaceId = (securityIdentity.principal as SaasPrincipal).workspaceId
        if (workspaceId != null) {
            return UserWorkspaceMapping.fromEntity(workspaceService.getUserWorkspaceMapping(getUserId(), workspaceId))
                    ?: throw HttpResponseException(Response.Status.FORBIDDEN.statusCode, "User is not a member of the workspace")
        }
        throw HttpResponseException(Response.Status.FORBIDDEN.statusCode, "Api needs to be called in the context of a workspace")
    }
}