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
        var worspaceUserId = EntityUtil.genWorkspaceUserId()
        if (isUserDefaultWorkspace) {
            workspaceId = EntityUtil.genWorkspaceId(EntityUtil.retrieve(userId))
            worspaceUserId = EntityUtil.genWorkspaceUserId(EntityUtil.retrieve(userId))
        }

        val workspaceEntity = WorkspaceEntity().apply {
            this.id = workspaceId
            this.name = workspaceName
        }

        val userWorkspaceMappingEntity = UserWorkspaceMappingEntity().apply {
            this.workspaceId = workspaceEntity.id
            this.userId = userId
            this.workspaceUserId = worspaceUserId
            this.workspaceUserName = workspaceUserName
            this.role = UserWorkspaceRole.Admin
        }

        workspaceEntityRepository.persist(workspaceEntity)
        userWorkspaceMappingEntityRepository.persist(userWorkspaceMappingEntity)
        return Workspace.fromWorkspaceEntity(workspaceEntity)
    }

    fun listUserWorspaces(userId: String): List<UserWorkspace> {
        val mappings = userWorkspaceMappingEntityRepository.listWorkspaces(userId)
        val workspaces = workspaceEntityRepository.listWorkspaces(mappings.map { it.workspaceId })
        return mappings.map { mapping ->
            val workspace = workspaces.find { it.id == mapping.workspaceId }
            UserWorkspace.fromUserWorkspaceMappingEntity(mapping, workspace!!.name!!)
        }
    }
}