package org.clevercastle.saas.core.account

import org.clevercastle.saas.base.TimeUtils
import org.clevercastle.saas.base.account.OidcProvider
import org.clevercastle.saas.core.iam.IAMService
import org.clevercastle.saas.core.jwt.TokenHolder
import org.clevercastle.saas.core.model.account.UserEntity
import org.clevercastle.saas.core.model.account.UserEntityRepository
import org.clevercastle.saas.core.model.account.UserOIDCMapping
import org.clevercastle.saas.core.model.account.UserOIDCMappingRepository
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

    lateinit var iamService: IAMService

    /**
     *  1. create user in auth0...
     *  2. create user in table
     *  3 create default workspace for the user
     *
     */
    @Transactional
    fun register(email: String, password: String, name: String): User {
        val identityServerUser = iamService!!.registerUser(email, password, mapOf())
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
        return iamService!!.login(email, password)
    }
}