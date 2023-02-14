package org.clevercastle.saas.core.account

import org.clevercastle.saas.core.iam.IAMService
import org.clevercastle.saas.core.jwt.TokenHolder
import org.clevercastle.saas.core.model.EntityPrefix
import org.clevercastle.saas.core.model.account.*
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.Transactional

@ApplicationScoped
class UserService {
    @Inject
    private lateinit var userRepository: UserEntityRepository

    @Inject
    private lateinit var userTenantMappingEntityRepository: UserTenantMappingEntityRepository

    @Inject
    private lateinit var tenantEntityRepository: TenantEntityRepository

    @Inject
    private lateinit var iamService: IAMService

    /**
     *  1. create user in cognito
     *  2. create user in table
     *  3 create default tenant for the user
     *
     */
    @Transactional
    fun register(email: String, password: String, name: String): User {
        // check if user already exists (email)
        iamService.registerUser(email, password, mapOf())
        val userEntity = UserEntity().apply {
            this.defaultTenantId = "${EntityPrefix.tenant}${EntityPrefix.retrieve(this.id)}"
            this.email = email
        }
        val tenantEntity = TenantEntity().apply {
            this.id = userEntity.defaultTenantId
            this.name = name
        }
        val userTenantMappingEntity = UserTenantMappingEntity().apply {
            this.tenantId = tenantEntity.id
            this.userId = userEntity.id
            this.tenantUserId = "${EntityPrefix.tenantUser}${UUID.randomUUID()}"
            this.tenantUserName = name
        }
        userRepository.persist(userEntity)
        tenantEntityRepository.persist(tenantEntity)
        userTenantMappingEntityRepository.persist(userTenantMappingEntity)
        return User.converter.fromUserEntity(userEntity)
    }

    fun login(email: String, password: String): TokenHolder {
        return iamService.login(email, password)
    }
}