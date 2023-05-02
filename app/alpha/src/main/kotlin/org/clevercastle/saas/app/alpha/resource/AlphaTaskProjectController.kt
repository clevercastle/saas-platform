package org.clevercastle.saas.app.alpha.resource

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.apache.commons.lang3.StringUtils
import org.clevercastle.saas.core.app.alpha.task.AlphaTaskProjectService
import org.clevercastle.saas.core.app.alpha.task.bo.CreateAlphaTaskProjectBO
import org.clevercastle.saas.core.internal.auth.SecurityService
import org.clevercastle.saas.model.app.ResourceOwnerType
import org.clevercastle.saas.model.app.alpha.AlphaTaskProject

@Path("alpha/project")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AlphaTaskProjectController {

    @Inject
    private lateinit var securityService: SecurityService

    @Inject
    private lateinit var alphaTaskProjectService: AlphaTaskProjectService

    @POST
    @Path("")
    fun createProject(@Valid req: CreateProjectReq): AlphaTaskProject {
        securityService.getUserId()
        val createAlphaTaskProjectBO = CreateAlphaTaskProjectBO().apply {
            this.workspaceId = securityService.getAccount().workspaceId
            if (StringUtils.isBlank(req.teamId)) {
                this.ownerId = securityService.getUserId()
                this.ownerType = ResourceOwnerType.User
            } else {
                this.ownerId = req.teamId
                this.ownerType = ResourceOwnerType.Team
            }
            this.name = req.name!!
            this.liked = req.liked
        }
        return alphaTaskProjectService.createTaskProject(createAlphaTaskProjectBO)
    }

    @GET
    @Path("")
    fun listProject(@QueryParam("ownerId") ownerId: String): List<AlphaTaskProject> {
        return alphaTaskProjectService.listTaskProject(ownerId)
    }
}

data class CreateProjectReq(
    @field:NotBlank(message = "Team id is required")
    val teamId: String,
    @field:NotBlank(message = "Name is required")
    val name: String?,
    val liked: Boolean = false,
)
