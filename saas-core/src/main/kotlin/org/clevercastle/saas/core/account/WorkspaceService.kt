package org.clevercastle.saas.core.account

import org.clevercastle.saas.core.internal.auth.SecurityService
import org.clevercastle.saas.core.internal.exception.BadRequestException
import org.clevercastle.saas.core.internal.exception.NotFoundException
import org.clevercastle.saas.core.model.EntityUtil
import org.clevercastle.saas.core.model.account.*
import org.clevercastle.saas.util.TimeUtils
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.RollbackException
import javax.transaction.Transactional

@ApplicationScoped
class WorkspaceService {
    @Inject
    private lateinit var userWorkspaceMappingEntityRepository: UserWorkspaceMappingEntityRepository

    @Inject
    private lateinit var workspaceEntityRepository: WorkspaceEntityRepository

    @Inject
    private lateinit var userWorkspaceTeamMappingEntityRepository: UserWorkspaceTeamMappingEntityRepository

    @Inject
    private lateinit var workspaceTeamEntityRepository: WorkspaceTeamEntityRepository

    @Inject
    private lateinit var securityService: SecurityService

    // region workspace

    @Transactional
    fun createWorkspace(userId: String, workspaceName: String, workspaceUserName: String): Workspace {
        val workspaceId = EntityUtil.Companion.Account.genWorkspaceId()
        val workspaceEntity = WorkspaceEntity().apply {
            this.id = workspaceId
            this.name = workspaceName
            this.createdAt = TimeUtils.now()
            this.updatedAt = TimeUtils.now()
            this.createdBy = this.id
            this.updatedBy = this.id
        }

        val userWorkspaceMappingEntity = UserWorkspaceMappingEntity().apply {
            this.userId = userId
            this.workspaceId = workspaceEntity.id
            this.workspaceUserName = workspaceUserName
            this.role = WorkspaceUserRole.Admin
            this.createdAt = TimeUtils.now()
            this.updatedAt = TimeUtils.now()
            this.createdBy = this.id
            this.updatedBy = this.id
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
            WorkspaceUser.fromEntity(mapping, workspace!!.name!!)
        }
    }

    @Transactional
    fun joinWorkspace(userId: String, workspaceId: String, pWorkspaceUsername: String, role: WorkspaceUserRole) {
        val userWorkspaceMappingEntity = UserWorkspaceMappingEntity().apply {
            this.userId = userId
            this.workspaceId = workspaceId
            this.workspaceUserName = pWorkspaceUsername
            this.role = role
            this.createdAt = TimeUtils.now()
            this.updatedAt = TimeUtils.now()
            this.createdBy = securityService.getUserId()
            this.updatedBy = securityService.getUserId()
        }
        userWorkspaceMappingEntityRepository.persist(userWorkspaceMappingEntity)
    }

    @Transactional
    fun updateWorkspace(userId: String, workspaceId: String, pWorkspaceUsername: String?, role: WorkspaceUserRole?) {
        val userWorkspaceMappingEntity = userWorkspaceMappingEntityRepository.getByUserIdAndWorkspaceId(userId, workspaceId)
                ?: throw NotFoundException("User is not a member of the workspace")
        if (pWorkspaceUsername != null) {
            userWorkspaceMappingEntity.workspaceUserName = pWorkspaceUsername
        }
        if (role != null) {
            userWorkspaceMappingEntity.role = role
        }
        userWorkspaceMappingEntity.updatedAt = TimeUtils.now()
        userWorkspaceMappingEntity.updatedBy = securityService.getUserId()
        userWorkspaceMappingEntityRepository.persist(userWorkspaceMappingEntity)
    }

    fun getUserWorkspaceMapping(userId: String, workspaceId: String): UserWorkspaceMappingEntity? {
        return userWorkspaceMappingEntityRepository.getByUserIdAndWorkspaceId(userId, workspaceId)
    }

    // endregion

    // region workspace team

    fun listUserWorkspaceTeams(userId: String, workspaceId: String): List<UserWorkspaceTeam> {
        val mappings = userWorkspaceTeamMappingEntityRepository.listUserWorkspaceTeamMappings(userId, workspaceId)
        val workspaceTeams = workspaceTeamEntityRepository.listWorkspaceTeams(mappings.map { it.workspaceTeamId })
        return workspaceTeams.map { team ->
            val mapping = mappings.find { it.workspaceTeamId == team.id }!!
            UserWorkspaceTeam.fromEntity(team, mapping)
        }
    }

    @Transactional
    fun createWorkspaceTeam(userId: String, workspaceId: String, name: String, description: String?): WorkspaceTeam {
        val workspaceTeamEntity = WorkspaceTeamEntity().apply {
            this.workspaceId = workspaceId
            this.name = name
            this.description = description
            this.createdAt = TimeUtils.now()
            this.createdBy = userId
            this.updatedAt = TimeUtils.now()
            this.updatedBy = userId
        }
        val userWorkspaceTeamEntity = UserWorkspaceTeamMappingEntity().apply {
            this.userId = userId
            this.workspaceId = workspaceId
            this.workspaceTeamId = workspaceTeamEntity.id
            this.userInWorkspaceTeamRole = UserInWorkspaceTeamRole.Admin
            this.createdAt = TimeUtils.now()
            this.createdBy = userId
            this.updatedAt = TimeUtils.now()
            this.updatedBy = userId
        }
        workspaceTeamEntity.persist()
        userWorkspaceTeamEntity.persist()
        return WorkspaceTeam.fromEntity(workspaceTeamEntity)
    }

    @Transactional
    fun joinWorkspaceTeam(userId: String, workspaceId: String, workspaceTeamId: String, role: UserInWorkspaceTeamRole) {
        val workspaceTeamEntity = workspaceTeamEntityRepository.findById(workspaceTeamId)
                ?: throw NotFoundException("Workspace team not found")
        if (workspaceTeamEntity.workspaceId != workspaceId) {
            throw BadRequestException("Workspace team does not belong to the workspace")
        }
        val userWorkspaceTeamMappingEntity = UserWorkspaceTeamMappingEntity().apply {
            this.userId = userId
            this.workspaceId = workspaceId
            this.workspaceTeamId = workspaceTeamId
            this.userInWorkspaceTeamRole = role
            this.createdAt = TimeUtils.now()
            this.updatedAt = TimeUtils.now()
            this.createdBy = securityService.getUserId()
            this.updatedBy = securityService.getUserId()
        }
        userWorkspaceTeamMappingEntity.persist()
    }

    @Transactional
    fun leaveWorkspaceTeam(userId: String, workspaceId: String, workspaceTeamId: String) {
        val count = userWorkspaceTeamMappingEntityRepository.delete(userId, workspaceId, workspaceTeamId)
        if (count > 1) {
            throw RollbackException("More than one user workspace team mapping is deleted")
        }
    }
    // endregion


}