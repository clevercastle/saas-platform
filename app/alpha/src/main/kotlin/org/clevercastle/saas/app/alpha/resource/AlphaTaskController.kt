package org.clevercastle.saas.app.alpha.resource

import org.apache.commons.lang3.StringUtils
import org.clevercastle.saas.core.alpha.task.AlphaTask
import org.clevercastle.saas.core.alpha.task.AlphaTaskService
import org.clevercastle.saas.core.alpha.task.bo.CreateAlphaTaskBO
import org.clevercastle.saas.core.internal.auth.SecurityService
import org.clevercastle.saas.core.internal.exception.NotFoundException
import org.clevercastle.saas.core.model.EntityUtil
import org.clevercastle.saas.core.model.ResourceOwnerType
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("alpha/project/{projectId}")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AlphaTaskController {

    @Inject
    private lateinit var securityService: SecurityService

    @Inject
    private lateinit var alphaTaskService: AlphaTaskService

    @POST
    @Path("task")
    fun createTask(@PathParam("projectId") projectId: String, @Valid req: CreateTaskReq): AlphaTask {
        val createAlphaTaskBO = CreateAlphaTaskBO().apply {
            this.workspaceId = securityService.getUserWorkspaceMapping().workspaceId
            if (StringUtils.isBlank(req.teamId)) {
                this.ownerId = securityService.getUserId()
                this.ownerType = ResourceOwnerType.User
            } else {
                this.ownerId = req.teamId!!
                this.ownerType = ResourceOwnerType.Team
            }
            // TODO: check project id is valid (project id is valid if it is owned by the ownerId)
            this.projectId = projectId
            this.name = req.name!!
            this.taskPath = req.taskPath!!
            this.groupId = EntityUtil.Companion.Alpha.genTaskGroupId()
        }
        return alphaTaskService.createTask(createAlphaTaskBO)
    }

    @GET
    @Path("task")
    fun listTask(@PathParam("projectId") projectId: String, @QueryParam("ownerId") ownerId: String): List<AlphaTask> {
        return alphaTaskService.listByOwnerId(ownerId, projectId)
    }

    @POST
    @Path("task/{taskId}/version")
    fun createTaskVersion(@PathParam("taskId") taskId: String): AlphaTask {
        // TODO: check task id is valid (task id is valid if it is owned by the ownerId)
        // TODO: global lock to make sure that only one version is created at a time
        // TODO: should be get largest version number and create a new version with version number + 1
        val originalTask = alphaTaskService.getByTaskId(taskId) ?: throw NotFoundException("Original Task not found")
        val latestTask = alphaTaskService.listByGroupId(originalTask.groupId).maxBy { it.version }
        return alphaTaskService.createVersion(latestTask)
    }
}

data class CreateTaskReq(
        val teamId: String?,
        @field:NotBlank(message = "Task name is required")
        val name: String?,
        @field:NotBlank(message = "Task path is required")
        val taskPath: String?,
)