package org.clevercastle.saas.core.account

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.core.Response
import org.clevercastle.saas.core.internal.exception.HttpResponseException
import org.clevercastle.saas.model.core.account.WorkspaceUserRole

@ApplicationScoped
class PermissionService {
    @Inject
    private lateinit var workspaceService: WorkspaceService

    fun canAccessWorkspace(userId: String, workspaceId: String, roles: List<WorkspaceUserRole>, throwException: Boolean = true): Boolean {

        val userWorkspaceMappingEntity = workspaceService.getUserWorkspaceMapping(userId, workspaceId)
                ?: if (throwException) {
                    throw HttpResponseException(Response.Status.FORBIDDEN.statusCode, null, "Operator is not a member of the workspace")
                } else {
                    return false
                }
        if (roles.contains(userWorkspaceMappingEntity.role)) {
            return true
        }
        if (throwException) {
            throw HttpResponseException(Response.Status.FORBIDDEN.statusCode, null, "Operator does not have permission to do the action on this workspace")
        } else {
            return false
        }
    }

//    fun canAccessTeam(userId: String, workspaceId: String, ownerId: String, ownerType: ResourceOwnerType, throwException: Boolean = true): Boolean {
//        val mapping = workspaceService.getUserWorkspaceMapping(userId, workspaceId)
//                ?: if (throwException) {
//                    throw HttpResponseException(Response.Status.FORBIDDEN.statusCode, null, "Operator does not have permission to access the workspace")
//                } else {
//                    return false
//                }
//        when (ownerType) {
//            ResourceOwnerType.User -> {
//                if (mapping.workspaceUserId == ownerId) {
//                    return true
//                }
//            }
//            ResourceOwnerType.Team -> {
//                if () {
//                    return true
//                }
//            }
//        }
//    }

}