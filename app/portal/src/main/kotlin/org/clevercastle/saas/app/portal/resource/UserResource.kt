package org.clevercastle.saas.app.portal.resource

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.clevercastle.saas.app.common.vo.AccountVO
import org.clevercastle.saas.app.common.vo.AccountVOConverter
import org.clevercastle.saas.app.common.vo.UserVO
import org.clevercastle.saas.core.account.UserService
import org.clevercastle.saas.core.account.WorkspaceService
import org.clevercastle.saas.core.internal.auth.SecurityService
import org.clevercastle.saas.core.internal.exception.HttpResponseException

@Path("portal/user")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class UserResource {
    private lateinit var userService: UserService
    private lateinit var securityService: SecurityService
    private lateinit var workspaceService: WorkspaceService

    constructor()

    @Inject
    constructor(userService: UserService, securityService: SecurityService, workspaceService: WorkspaceService) {
        this.userService = userService
        this.securityService = securityService
        this.workspaceService = workspaceService
    }

    @GET
    @Path("")
    fun getUser(): UserVO {
        val userId = this.securityService.getUserId()
        return UserVO.fromUser(userService.getByUserId(userId))
            ?: throw HttpResponseException(Response.Status.FORBIDDEN.statusCode, "User not found")
    }

    @GET
    @Path("account")
    fun listAccounts(): List<AccountVO> {
        val userId = securityService.getUserId()
        val accounts = workspaceService.listAccountByUser(userId)
        val workspaces = workspaceService.listWorkspaces(accounts.map { it.workspaceId })
        return accounts.mapNotNull { mapping ->
            val workspace = workspaces.find { it.id == mapping.workspaceId }
            if (workspace != null) {
                AccountVOConverter.fromAccountAndWorkspace(mapping, workspace!!)
            } else {
                null
            }
        }
    }

}