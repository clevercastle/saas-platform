package org.clevercastle.saas.core.account

import org.clevercastle.saas.core.iam.IAMService
import org.clevercastle.saas.core.jwt.TokenHolder
import org.clevercastle.saas.core.model.EntityUtil
import org.clevercastle.saas.core.model.account.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.Transactional

@ApplicationScoped
class AuthService {
    @Inject
    private lateinit var userRepository: UserEntityRepository

    @Inject
    private lateinit var userOIDCMappingRepository: UserOIDCMappingRepository

    @Inject
    private lateinit var workspaceService: WorkspaceService

    @Inject
    private lateinit var iamService: IAMService

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
            this.defaultWorkspaceId = EntityUtil.Companion.Account.genWorkspaceId(EntityUtil.retrieve(this.id))
            this.email = email
        }
        val userOIDCMapping = UserOIDCMapping().apply {
            this.userId = userEntity.id
            this.userSub = identityServerUser.userSub
            this.oidcProvider = OidcProvider.Auth0
        }
        userRepository.persist(userEntity)
        userOIDCMappingRepository.persist(userOIDCMapping)
        return User.fromUserEntity(userEntity)!!
    }

    fun login(email: String, password: String): TokenHolder {
        return iamService.login(email, password)
    }
}