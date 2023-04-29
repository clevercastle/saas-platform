package org.clevercastle.saas.app.portal.resource

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.persistence.PersistenceException
import jakarta.transaction.RollbackException
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.clevercastle.saas.app.common.vo.UserVO
import org.clevercastle.saas.app.common.vo.UserWorkspaceVO
import org.clevercastle.saas.app.common.vo.WorkspaceTeamVO
import org.clevercastle.saas.app.common.vo.WorkspaceVO
import org.clevercastle.saas.app.portal.model.request.CreateWorkspaceReq
import org.clevercastle.saas.app.portal.model.request.JoinWorkspaceReq
import org.clevercastle.saas.core.account.PermissionService
import org.clevercastle.saas.core.account.UserService
import org.clevercastle.saas.core.account.WorkspaceService
import org.clevercastle.saas.core.internal.auth.SecurityService
import org.clevercastle.saas.core.internal.exception.HttpResponseException
import org.clevercastle.saas.core.internal.validation.EnumValidator
import org.clevercastle.saas.model.core.account.UserInWorkspaceTeamRole
import org.clevercastle.saas.model.core.account.UserWorkspaceTeam
import org.clevercastle.saas.model.core.account.WorkspaceUserRole

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

    @Inject
    private lateinit var permissionService: PermissionService

    constructor()

    @Inject
    constructor(
        securityService: SecurityService,
        userService: UserService,
        workspaceService: WorkspaceService,
        permissionService: PermissionService
    ) {
        this.securityService = securityService
        this.userService = userService
        this.workspaceService = workspaceService
        this.permissionService = permissionService
    }


    @GET
    @Path("account")
    fun getAccount(): UserVO {
        return UserVO.fromUser(userService.getUserByUserId(securityService.getUserId())!!)
    }

    @POST
    @Path("workspace")
    fun createWorkspace(@Valid req: CreateWorkspaceReq): WorkspaceVO {
        val workspace =
            workspaceService.createWorkspace(securityService.getUserId(), req.name!!, req.workspaceUserName!!)
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
    fun joinWorkspace(@Valid req: JoinWorkspaceReq): Response {
        permissionService.canAccessWorkspace(
            securityService.getUserId(),
            req.workspaceId!!,
            listOf(WorkspaceUserRole.Admin)
        )
        try {
            workspaceService.joinWorkspace(
                req.userId!!,
                req.workspaceId,
                req.workspaceUserName!!,
                WorkspaceUserRole.valueOf(req.workspaceUserRole!!)
            )
        } catch (e: RollbackException) {
            throw HttpResponseException(httpStatus = 400, message = "User is already in workspace.")
        }
        return Response.ok().build()
    }

    @PUT
    @Path("workspace/{workspaceId}/user/{userId}")
    fun updateWorkspaceUserRole(
        @PathParam("workspaceId") workspaceId: String, @PathParam("userId") userId: String,
        @Valid req: AdminUpdateWorkspaceUserRoleReq
    ): Response {
        permissionService.canAccessWorkspace(securityService.getUserId(), workspaceId, listOf(WorkspaceUserRole.Admin))
        if (req.workspaceUserRole == null) {
            workspaceService.updateWorkspace(userId, workspaceId, req.workspaceUserName, null)
        } else {
            workspaceService.updateWorkspace(
                userId,
                workspaceId,
                req.workspaceUserName,
                WorkspaceUserRole.valueOf(req.workspaceUserRole)
            )
        }
        return Response.ok().build()
    }

    @GET
    @Path("workspace/{workspaceId}/team")
    fun getWorkspaceTeam(@PathParam("workspaceId") workspaceId: String): List<UserWorkspaceTeam> {
        val userWorkspaceMapping = securityService.getUserWorkspaceMapping()
        if (userWorkspaceMapping.workspaceId != workspaceId) {
            throw HttpResponseException(
                httpStatus = Response.Status.BAD_REQUEST.statusCode,
                message = "The workspace id in path is not correct"
            )
        }
        return workspaceService.listUserWorkspaceTeams(securityService.getUserId(), userWorkspaceMapping.workspaceId)
    }

    @POST
    @Path("workspace/{workspaceId}/team")
    fun createWorkspaceTeam(
        @PathParam("workspaceId") workspaceId: String,
        @Valid req: CreateWorkspaceTeamReq
    ): WorkspaceTeamVO {
        val userWorkspaceMapping = securityService.getUserWorkspaceMapping()
        if (userWorkspaceMapping.workspaceId != workspaceId) {
            throw HttpResponseException(
                httpStatus = Response.Status.BAD_REQUEST.statusCode,
                message = "The workspace id in path is not correct"
            )
        }
        val workspaceTeam = workspaceService.createWorkspaceTeam(
            securityService.getUserId(), userWorkspaceMapping.workspaceId,
            req.name!!, req.description
        )
        return WorkspaceTeamVO.fromWorkspaceTeam(workspaceTeam)
    }

    /**
     * Join a workspace team
     */
    @PUT
    @Path("workspace/{workspaceId}/team/join")
    fun joinWorkspaceTeam(@PathParam("workspaceId") workspaceId: String, @Valid req: JoinWorkspaceTeamReq): Response {
        // TODO: check the operator is the admin of the team  or the admin of the workspace
        val userWorkspaceMapping = securityService.getUserWorkspaceMapping()
        if (userWorkspaceMapping.workspaceId != workspaceId) {
            throw HttpResponseException(
                httpStatus = Response.Status.BAD_REQUEST.statusCode,
                message = "The workspace id in path is not correct"
            )
        }
        try {
            workspaceService.joinWorkspaceTeam(
                req.userId!!,
                userWorkspaceMapping.workspaceId,
                req.workspaceTeamId!!,
                req.role
            )
        } catch (e: RollbackException) {
            when (e.cause?.javaClass) {
                PersistenceException::class.java -> {
                    throw HttpResponseException(httpStatus = 400, message = "User is already in workspace team.")
                }
                else -> {
                    throw HttpResponseException(httpStatus = 500, message = "")
                }
            }
        }
        return Response.ok().build()
    }

    /**
     * Join a workspace team
     */
    @PUT
    @Path("workspace/{workspaceId}/team/{workspaceTeamId}/leave")
    fun leaveWorkspaceTeam(
        @PathParam("workspaceId") workspaceId: String,
        @PathParam("workspaceTeamId") workspaceTeamId: String, @Valid req: LeaveWorkspaceTeamReq
    ): Response {
        // TODO: check the operator is the admin of the team  or the admin of the workspace
        val userWorkspaceMapping = securityService.getUserWorkspaceMapping()
        if (userWorkspaceMapping.workspaceId != workspaceId) {
            throw HttpResponseException(
                httpStatus = Response.Status.BAD_REQUEST.statusCode,
                message = "The workspace id in path is not correct"
            )
        }
        workspaceService.leaveWorkspaceTeam(
            req.userId!!,
            securityService.getUserWorkspaceMapping().workspaceId,
            workspaceTeamId
        )
        return Response.ok().build()
    }
}

class CreateWorkspaceTeamReq {
    @field:NotEmpty(message = "Team name is required")
    var name: String? = null
    var description: String? = null
}

class JoinWorkspaceTeamReq {
    @field:NotEmpty(message = "UserId is required")
    var userId: String? = null

    @field:NotEmpty(message = "Workspace team id is required")
    var workspaceTeamId: String? = null
    var role: UserInWorkspaceTeamRole = UserInWorkspaceTeamRole.ReadOnlyUser
}

class LeaveWorkspaceTeamReq {
    @field:NotEmpty(message = "UserId is required")
    var userId: String? = null
}

class AdminUpdateWorkspaceUserRoleReq(
    @field:EnumValidator(WorkspaceUserRole::class, message = "Invalid role", method = "name")
    val workspaceUserRole: String?,
    val workspaceUserName: String?
)