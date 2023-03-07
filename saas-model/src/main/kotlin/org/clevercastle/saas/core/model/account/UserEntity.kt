package org.clevercastle.saas.core.model.account

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import org.clevercastle.saas.core.model.BaseEntity
import org.clevercastle.saas.core.model.EntityUtil
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.persistence.Entity
import javax.persistence.Id

enum class OidcProvider {
    Auth0,
    Cognito
}

@Entity(name = "users")
class UserEntity : BaseEntity() {
    companion object : PanacheCompanionBase<UserEntity, String>

    @Id
    var id: String = EntityUtil.genUserId()
    lateinit var defaultTenantId: String
    lateinit var email: String
}


@Entity(name = "user_oidc_mapping")
class UserOIDCMapping : BaseEntity() {
    @Id
    var id: String = UUID.randomUUID().toString()
    lateinit var userId: String
    lateinit var userSub: String
    lateinit var oidcProvider: OidcProvider
}

@ApplicationScoped
class UserEntityRepository : PanacheRepositoryBase<UserEntity, String>

@ApplicationScoped
class UserOIDCMappingRepository : PanacheRepositoryBase<UserOIDCMapping, String> {
    fun getUserIdByUserSub(userSub: String): String? {
        return find("user_sub", userSub).firstResult()?.userId
    }
}