package org.clevercastle.saas.app.portal.resource

import org.clevercastle.saas.app.common.auth.SecurityService
import org.clevercastle.saas.app.common.vo.UserVO
import org.clevercastle.saas.app.common.vo.UserWorkspaceVO
import org.clevercastle.saas.app.common.vo.WorkspaceVO
import org.clevercastle.saas.app.portal.model.request.CreateWorkspaceReq
import org.clevercastle.saas.app.portal.model.request.JoinWorkspaceReq
import org.clevercastle.saas.core.account.UserService
import org.clevercastle.saas.core.account.WorkspaceService
import org.clevercastle.saas.core.internal.exception.HttpResponseException
import org.clevercastle.saas.core.internal.validation.EnumValidator
import org.clevercastle.saas.core.model.account.WorkspaceUserRole
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.RollbackException
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("portal")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AccountResource {
    @Inject
    private lateinit var securityService: SecurityService

    @Inject
    private lateinit var userService: UserService

    @Inject
    private lateinit var workspaceService: WorkspaceService

    @GET
    @Path("account")
    fun getAccount(): UserVO {
        return UserVO.fromUser(userService.getUserByUserId(securityService.getUserId())!!)
    }

    @POST
    @Path("workspace")
    fun createWorkspace(@Valid req: CreateWorkspaceReq): WorkspaceVO {
        val workspace = workspaceService.createWorkspace(securityService.getUserId(), req.name!!, req.workspaceUserName!!)
        return WorkspaceVO.fromWorkspace(workspace)
    }

    @GET
    @Path("workspace")
    fun listUserWorkspaces(): List<UserWorkspaceVO> {
        val userId = securityService.getUserId()
        return workspaceService.listWorkspaceUsersByUserId(userId).map { UserWorkspaceVO.fromUserWorkspace(it) }
    }

    @PUT
    @Path("workspace/join")
    fun joinWorkspace(@Valid req: JoinWorkspaceReq) {
        // TODO: check if principal is the admin of the workspace
        val userId = securityService.getUserId()
        try {
            workspaceService.joinWorkspace(userId, req.workspaceId!!, req.workspaceUserName!!, WorkspaceUserRole.valueOf(req.workspaceUserRole!!))
        } catch (e: RollbackException) {
            throw HttpResponseException(httpStatus = 400, message = "User is already in workspace.")
        }
    }

    @PUT
    @Path("workspace/{workspaceId}/user/{userId}")
    fun updateWorkspaceUserRole(@PathParam("workspaceId") workspaceId: String, @PathParam("userId") userId: String,
                                @Valid req: AdminUpdateWorkspaceUserRoleReq) {
        // TODO: check if principal is the admin of the workspace
        if (req.workspaceUserRole == null) {
            workspaceService.updateWorkspace(userId, workspaceId, req.workspaceUserName, null)
        } else {
            workspaceService.updateWorkspace(userId, workspaceId, req.workspaceUserName, WorkspaceUserRole.valueOf(req.workspaceUserRole))
        }
    }
}

class AdminUpdateWorkspaceUserRoleReq(
        @field:EnumValidator(WorkspaceUserRole::class, message = "Invalid role", method = "name")
        val workspaceUserRole: String?,
        val workspaceUserName: String?)