package org.clevercastle.saas.core.account

import org.clevercastle.saas.core.internal.exception.HttpResponseException
import org.clevercastle.saas.core.model.account.WorkspaceUserRole
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.core.Response

@ApplicationScoped
class PermissionService {
    @Inject
    private lateinit var workspaceService: WorkspaceService

    fun canAccessWorkspace(userId: String, workspaceId: String, roles: List<WorkspaceUserRole>, throwException: Boolean = true): Boolean {

        val userWorkspaceMappingEntity = workspaceService.getUserWorkspaceMapping(userId, workspaceId)
                ?: if (throwException) {
                    throw HttpResponseException(Response.Status.FORBIDDEN.statusCode, null, "User is not a member of the workspace")
                } else {
                    return false
                }
        if (roles.contains(userWorkspaceMappingEntity.role)) {
            return true
        }
        if (throwException) {
            throw HttpResponseException(Response.Status.FORBIDDEN.statusCode, null, "User does not have permission to access the workspace")
        } else {
            return false
        }
    }
}