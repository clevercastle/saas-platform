package org.clevercastle.saas.app.portal.resource

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.clevercastle.saas.app.common.vo.WorkspaceVO
import org.clevercastle.saas.app.portal.model.request.CreateWorkspaceReq
import org.clevercastle.saas.core.account.DepartmentService
import org.clevercastle.saas.core.account.PermissionService
import org.clevercastle.saas.core.account.UserService
import org.clevercastle.saas.core.account.WorkspaceService
import org.clevercastle.saas.core.internal.auth.SecurityService

@Path("portal")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class WorkspaceResource {

    private lateinit var securityService: SecurityService
    private lateinit var userService: UserService
    private lateinit var workspaceService: WorkspaceService
    private lateinit var permissionService: PermissionService
    private lateinit var departmentService: DepartmentService
    private lateinit var teamService: DepartmentService

    constructor()

    @Inject
    constructor(
        securityService: SecurityService,
        userService: UserService,
        workspaceService: WorkspaceService,
        permissionService: PermissionService,
        departmentService: DepartmentService,
        teamService: DepartmentService
    ) {
        this.securityService = securityService
        this.userService = userService
        this.workspaceService = workspaceService
        this.permissionService = permissionService
        this.departmentService = departmentService
        this.teamService = teamService
    }

    @POST
    @Path("workspace")
    fun createWorkspace(@Valid req: CreateWorkspaceReq): WorkspaceVO {
        val workspace =
            workspaceService.createWorkspace(securityService.getUserId(), req.name!!, req.accountName!!)
        return WorkspaceVO.fromWorkspace(workspace)
    }

}


