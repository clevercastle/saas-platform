package org.clevercastle.saas.app.portal.resource

import org.clevercastle.saas.app.common.auth.SecurityService
import org.clevercastle.saas.app.common.vo.UserVO
import org.clevercastle.saas.app.common.vo.UserWorkspaceVO
import org.clevercastle.saas.app.common.vo.WorkspaceVO
import org.clevercastle.saas.app.portal.model.request.CreateWorkspaceReq
import org.clevercastle.saas.core.account.UserService
import org.clevercastle.saas.core.account.WorkspaceService
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
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
        val workspace = workspaceService.createWorkspace(securityService.getUserId(), req.name!!, req.workspaceUserName!!, false)
        return WorkspaceVO.fromWorkspace(workspace)
    }

    @GET
    @Path("workspace")
    fun listUserWorkspaces(): List<UserWorkspaceVO> {
        val userId = securityService.getUserId()
        return workspaceService.listUserWorspaces(userId).map { UserWorkspaceVO.fromUserWorkspace(it) }
    }

    @PUT
    @Path("workspace/join")
    fun joinWorkspace(): List<UserWorkspaceVO> {
        val userId = securityService.getUserId()
        return workspaceService.listUserWorspaces(userId).map { UserWorkspaceVO.fromUserWorkspace(it) }
    }
}