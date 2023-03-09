package org.clevercastle.saas.core.account

import org.clevercastle.saas.core.model.EntityUtil
import org.clevercastle.saas.core.model.account.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.Transactional

@ApplicationScoped
class WorkspaceService {
    @Inject
    private lateinit var userWorkspaceMappingEntityRepository: UserWorkspaceMappingEntityRepository

    @Inject
    private lateinit var workspaceEntityRepository: WorkspaceEntityRepository

    @Transactional
    fun createWorkspace(userId: String, workspaceName: String, workspaceUserName: String, isUserDefaultWorkspace: Boolean): Workspace {
        var workspaceId = EntityUtil.genWorkspaceId()
        var workspaceUserId = EntityUtil.genWorkspaceUserId()
        if (isUserDefaultWorkspace) {
            workspaceId = EntityUtil.genWorkspaceId(EntityUtil.retrieve(userId))
            workspaceUserId = EntityUtil.genWorkspaceUserId(EntityUtil.retrieve(userId))
        }

        val workspaceEntity = WorkspaceEntity().apply {
            this.id = workspaceId
            this.name = workspaceName
        }

        val userWorkspaceMappingEntity = UserWorkspaceMappingEntity().apply {
            this.workspaceId = workspaceEntity.id
            this.userId = userId
            this.workspaceUserId = workspaceUserId
            this.workspaceUserName = workspaceUserName
            this.role = WorkspaceUserRole.Admin
        }

        workspaceEntityRepository.persist(workspaceEntity)
        userWorkspaceMappingEntityRepository.persist(userWorkspaceMappingEntity)
        return Workspace.fromWorkspaceEntity(workspaceEntity)
    }

    fun listWorkspaceUsersByUserId(userId: String): List<WorkspaceUser> {
        val mappings = userWorkspaceMappingEntityRepository.listWorkspaces(userId)
        val workspaces = workspaceEntityRepository.listWorkspaces(mappings.map { it.workspaceId })
        return mappings.map { mapping ->
            val workspace = workspaces.find { it.id == mapping.workspaceId }
            WorkspaceUser.fromUserWorkspaceMappingEntity(mapping, workspace!!.name!!)
        }
    }

    @Transactional
    fun joinWorkspace(userId: String, workspaceId: String, pWorkspaceUsername: String, role: WorkspaceUserRole) {
        val userWorkspaceMappingEntity = UserWorkspaceMappingEntity().apply {
            this.workspaceId = workspaceId
            this.userId = userId
            this.workspaceUserId = EntityUtil.genWorkspaceUserId()
            this.workspaceUserName = pWorkspaceUsername
            this.role = role
        }
        userWorkspaceMappingEntityRepository.persist(userWorkspaceMappingEntity)
    }

}