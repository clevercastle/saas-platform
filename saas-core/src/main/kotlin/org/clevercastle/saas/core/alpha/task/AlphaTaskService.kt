package org.clevercastle.saas.core.alpha.task

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.clevercastle.saas.base.IdUtil
import org.clevercastle.saas.base.alpha.AlphaTaskStatus
import org.clevercastle.saas.core.alpha.task.bo.CreateAlphaTaskBO
import org.clevercastle.saas.core.entity.alpha.AlphaTaskEntity
import org.clevercastle.saas.core.entity.alpha.AlphaTaskEntityRepository

@ApplicationScoped
class AlphaTaskService {
    @Inject
    private lateinit var alphaTaskEntityRepository: AlphaTaskEntityRepository

    fun getByTaskId(taskId: String): AlphaTask? {
        return AlphaTask.fromEntity(alphaTaskEntityRepository.getByTaskId(taskId))
    }

    fun listByGroupId(groupId: String): List<AlphaTask> {
        return alphaTaskEntityRepository.listByGroupId(groupId).map { AlphaTask.fromEntity(it)!! }
    }

    @Transactional
    fun createTask(createAlphaTaskBO: CreateAlphaTaskBO): AlphaTask {
        val alphaTaskEntity = AlphaTaskEntity().apply {
            this.id = IdUtil.Companion.Alpha.genTaskId()
            this.workspaceId = createAlphaTaskBO.workspaceId

            this.ownerId = createAlphaTaskBO.ownerId
            this.ownerType = createAlphaTaskBO.ownerType

            this.projectId = createAlphaTaskBO.projectId

            this.name = createAlphaTaskBO.name
            this.taskPath = createAlphaTaskBO.taskPath

            this.groupId = IdUtil.Companion.Alpha.genTaskGroupId()
            this.version = 0

            this.status = AlphaTaskStatus.Created
        }
        alphaTaskEntityRepository.persist(alphaTaskEntity)
        return AlphaTask.fromEntity(alphaTaskEntity)!!
    }

    fun listByOwnerId(ownerId: String, projectId: String): List<AlphaTask> {
        return alphaTaskEntityRepository.listByOwnerAndProject(ownerId, projectId).map { AlphaTask.fromEntity(it)!! }
    }

    @Transactional
    fun createVersion(originalTask: AlphaTask): AlphaTask {
        val alphaTaskEntity = AlphaTaskEntity().apply {
            this.id = IdUtil.Companion.Alpha.genTaskId()
            this.workspaceId = originalTask.workspaceId

            this.ownerId = originalTask.ownerId
            this.ownerType = originalTask.ownerType

            this.projectId = originalTask.projectId

            this.name = originalTask.name
            this.taskPath = originalTask.taskPath

            this.groupId = originalTask.groupId
            this.version = originalTask.version + 1

            this.status = AlphaTaskStatus.Created
        }
        alphaTaskEntityRepository.persist(alphaTaskEntity)
        return AlphaTask.fromEntity(alphaTaskEntity)!!

    }
}