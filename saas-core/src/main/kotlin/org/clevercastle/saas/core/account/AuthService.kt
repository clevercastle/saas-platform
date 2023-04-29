package org.clevercastle.saas.core.account

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.clevercastle.saas.base.TimeUtils
import org.clevercastle.saas.base.account.OidcProvider
import org.clevercastle.saas.core.entity.account.UserEntity
import org.clevercastle.saas.core.entity.account.UserEntityRepository
import org.clevercastle.saas.core.entity.account.UserOIDCMapping
import org.clevercastle.saas.core.entity.account.UserOIDCMappingRepository
import org.clevercastle.saas.core.iam.IAMService
import org.clevercastle.saas.core.jwt.TokenHolder

@ApplicationScoped
class AuthService {
    private lateinit var userRepository: UserEntityRepository

    private lateinit var userOIDCMappingRepository: UserOIDCMappingRepository

    private lateinit var workspaceService: WorkspaceService

    lateinit var iamService: IAMService

    constructor()

    @Inject
    constructor(
            userRepository: UserEntityRepository,
            userOIDCMappingRepository: UserOIDCMappingRepository,
            workspaceService: WorkspaceService,
            iamService: IAMService
    ) {
        this.userRepository = userRepository
        this.userOIDCMappingRepository = userOIDCMappingRepository
        this.workspaceService = workspaceService
        this.iamService = iamService
    }


    /**
     *  1. create user in auth0...
     *  2. create user in table
     *  3 create default workspace for the user
     *
     */
    @Transactional
    fun register(email: String, password: String, name: String): User {
        val identityServerUser = iamService.registerUser(email, password, mapOf())
        val userEntity = UserEntity().apply {
            this.email = email
            this.createdAt = TimeUtils.now()
            this.updatedAt = TimeUtils.now()
            this.createdBy = this.id
            this.updatedBy = this.id
        }
        val userOIDCMapping = UserOIDCMapping().apply {
            this.userId = userEntity.id
            this.userSub = identityServerUser.userSub
            this.oidcProvider = OidcProvider.Auth0
            this.createdAt = TimeUtils.now()
            this.updatedAt = TimeUtils.now()
            this.createdBy = this.id
            this.updatedBy = this.id
        }
        userRepository.persist(userEntity)
        userOIDCMappingRepository.persist(userOIDCMapping)
        return User.fromUserEntity(userEntity)!!
    }

    fun login(email: String, password: String): TokenHolder {
        return iamService.login(email, password)
    }
}