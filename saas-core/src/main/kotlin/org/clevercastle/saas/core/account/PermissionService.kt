package org.clevercastle.saas.core.account

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.core.Response
import org.clevercastle.saas.core.internal.exception.HttpResponseException
import org.clevercastle.saas.model.core.account.AccountRole

@ApplicationScoped
class PermissionService {
    private lateinit var accountService: AccountService

    constructor()

    @Inject
    constructor(accountService: AccountService) {
        this.accountService = accountService
    }


    fun canAccessWorkspace(
        userId: String,
        workspaceId: String,
        roles: List<AccountRole>,
        throwException: Boolean = true
    ): Boolean {

        val account = accountService.getByUserAndWorkspace(userId, workspaceId)
            ?: if (throwException) {
                throw HttpResponseException(
                    Response.Status.FORBIDDEN.statusCode,
                    null,
                    "Operator is not a member of the workspace"
                )
            } else {
                return false
            }
        if (roles.contains(account.role)) {
            return true
        }
        if (throwException) {
            throw HttpResponseException(
                Response.Status.FORBIDDEN.statusCode,
                null,
                "Operator does not have permission to do the action on this workspace"
            )
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