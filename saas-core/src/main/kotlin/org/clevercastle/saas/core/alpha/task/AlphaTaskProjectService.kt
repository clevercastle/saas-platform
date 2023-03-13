package org.clevercastle.saas.core.alpha.task

import org.clevercastle.saas.base.IdUtil
import org.clevercastle.saas.core.alpha.task.bo.CreateAlphaTaskProjectBO
import org.clevercastle.saas.core.model.alpha.AlphaTaskProjectEntity
import org.clevercastle.saas.core.model.alpha.AlphaTaskProjectEntityRepository
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.Transactional

@ApplicationScoped
class AlphaTaskProjectService {
    @Inject
    private lateinit var alphaTaskProjectEntityRepository: AlphaTaskProjectEntityRepository

    @Transactional
    fun createTaskProject(bo: CreateAlphaTaskProjectBO): AlphaTaskProject {
        val entity = AlphaTaskProjectEntity().apply {
            this.id = IdUtil.Companion.Alpha.genTaskProjectId()
            this.workspaceId = bo.workspaceId
            this.ownerId = bo.ownerId
            this.ownerType = bo.ownerType
            this.name = bo.name
            this.liked = bo.liked
        }
        alphaTaskProjectEntityRepository.persist(entity)
        return AlphaTaskProject.fromEntity(entity)
    }

    fun listTaskProject(ownerId: String): List<AlphaTaskProject> {
        return alphaTaskProjectEntityRepository.listByOwner(ownerId).map { AlphaTaskProject.fromEntity(it) }
    }

}