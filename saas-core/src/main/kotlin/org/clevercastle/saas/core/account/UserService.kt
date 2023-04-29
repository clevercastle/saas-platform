package org.clevercastle.saas.core.account

import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.context.control.ActivateRequestContext
import jakarta.inject.Inject
import org.clevercastle.saas.core.entity.account.UserEntityRepository
import org.clevercastle.saas.core.entity.account.UserOIDCMappingRepository

@ApplicationScoped
class UserService {
    @Inject
    private lateinit var userRepository: UserEntityRepository

    @Inject
    private lateinit var userOIDCMappingRepository: UserOIDCMappingRepository

    @Inject
    private lateinit var workspaceService: WorkspaceService


    @ActivateRequestContext
    fun getUserIdByUserSub(userSub: String): String? {
        return userOIDCMappingRepository.getUserIdByUserSub(userSub)
    }

    @ActivateRequestContext
    fun getUserByUserId(userId: String): User? {
        return User.fromUserEntity(userRepository.findById(userId))
    }
}