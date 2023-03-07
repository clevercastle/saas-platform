package org.clevercastle.saas.core.account

import org.clevercastle.saas.core.model.account.UserEntityRepository
import org.clevercastle.saas.core.model.account.UserOIDCMappingRepository
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.context.control.ActivateRequestContext
import javax.inject.Inject

@ApplicationScoped
class UserService {
    @Inject
    private lateinit var userRepository: UserEntityRepository

    @Inject
    private lateinit var userOIDCMappingRepository: UserOIDCMappingRepository

    @Inject
    private lateinit var tenantService: TenantService


    @ActivateRequestContext
    fun getUserIdByUserSub(userSub: String): String? {
        return userOIDCMappingRepository.getUserIdByUserSub(userSub)
    }

    @ActivateRequestContext
    fun getUserByUserId(userId: String): User? {
        return User.converter.fromUserEntity(userRepository.findById(userId))
    }
}