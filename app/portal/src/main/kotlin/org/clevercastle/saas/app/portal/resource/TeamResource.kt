package org.clevercastle.saas.app.portal.resource

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.clevercastle.saas.core.account.PermissionService
import org.clevercastle.saas.core.account.TeamService
import org.clevercastle.saas.core.account.UserService
import org.clevercastle.saas.core.account.WorkspaceService
import org.clevercastle.saas.core.internal.auth.SecurityService
import org.clevercastle.saas.core.internal.exception.HttpResponseException
import org.clevercastle.saas.model.core.account.Team

@Path("portal")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class TeamResource {

    private lateinit var securityService: SecurityService
    private lateinit var userService: UserService
    private lateinit var workspaceService: WorkspaceService
    private lateinit var permissionService: PermissionService
    private lateinit var teamService: TeamService

    constructor()

    @Inject
    constructor(
        securityService: SecurityService,
        userService: UserService,
        workspaceService: WorkspaceService,
        permissionService: PermissionService,
        teamService: TeamService
    ) {
        this.securityService = securityService
        this.userService = userService
        this.workspaceService = workspaceService
        this.permissionService = permissionService
        this.teamService = teamService
    }

    @GET
    @Path("workspace/{workspaceId}/team")
    fun getTeam(@PathParam("workspaceId") workspaceId: String): List<Team> {
        val userWorkspaceMapping = securityService.getAccount()
        if (userWorkspaceMapping.workspaceId != workspaceId) {
            throw HttpResponseException(
                httpStatus = Response.Status.BAD_REQUEST.statusCode,
                message = "The workspace id in path is not correct"
            )
        }
        return teamService.listTeam(userWorkspaceMapping.workspaceId)
    }
}