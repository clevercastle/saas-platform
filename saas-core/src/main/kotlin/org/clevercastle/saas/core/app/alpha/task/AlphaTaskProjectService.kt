package org.clevercastle.saas.core.app.alpha.task

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.clevercastle.saas.base.IdUtil
import org.clevercastle.saas.core.app.alpha.task.bo.CreateAlphaTaskProjectBO
import org.clevercastle.saas.entity.app.alpha.AlphaTaskProjectEntity
import org.clevercastle.saas.entity.app.alpha.AlphaTaskProjectEntityRepository
import org.clevercastle.saas.model.app.alpha.AlphaTaskProject

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
        return AlphaTaskProjectConverter.fromEntity(entity)
    }

    fun listTaskProject(ownerId: String): List<AlphaTaskProject> {
        return alphaTaskProjectEntityRepository.listByOwner(ownerId).map { AlphaTaskProjectConverter.fromEntity(it) }
    }

}