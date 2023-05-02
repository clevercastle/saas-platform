package org.clevercastle.saas.core.account

import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.context.control.ActivateRequestContext
import jakarta.inject.Inject
import org.clevercastle.saas.entity.core.account.UserEntityRepository
import org.clevercastle.saas.entity.core.account.UserOIDCMappingRepository
import org.clevercastle.saas.model.core.account.User

@ApplicationScoped
class UserService {
    private lateinit var userRepository: UserEntityRepository
    private lateinit var userOIDCMappingRepository: UserOIDCMappingRepository
    private lateinit var workspaceService: WorkspaceService

    constructor()

    @Inject
    constructor(
        userRepository: UserEntityRepository,
        userOIDCMappingRepository: UserOIDCMappingRepository,
        workspaceService: WorkspaceService
    ) {
        this.userRepository = userRepository
        this.userOIDCMappingRepository = userOIDCMappingRepository
        this.workspaceService = workspaceService
    }

    @ActivateRequestContext
    fun getUserIdByUserSub(userSub: String): String? {
        return userOIDCMappingRepository.getUserIdByUserSub(userSub)
    }

    @ActivateRequestContext
    fun getByUserId(userId: String): User? {
        return UserConverter.fromUserEntity(userRepository.findById(userId))
    }
}