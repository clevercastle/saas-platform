package org.clevercastle.saas.core.entity.account

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.clevercastle.saas.base.IdUtil
import org.clevercastle.saas.base.account.OidcProvider
import org.clevercastle.saas.base.account.OidcProviderHibernateConverter
import org.clevercastle.saas.core.entity.BaseEntity
import java.util.*

@Entity(name = "users")
class UserEntity : BaseEntity() {
    companion object : PanacheCompanionBase<UserEntity, String>

    @Id
    var id: String = IdUtil.Companion.Account.genUserId()
    lateinit var email: String
}


@Entity(name = "user_oidc_mapping")
class UserOIDCMapping : BaseEntity() {
    @Id
    var id: String = UUID.randomUUID().toString()
    lateinit var userId: String
    lateinit var userSub: String

    @field:Convert(converter = OidcProviderHibernateConverter::class)
    lateinit var oidcProvider: OidcProvider
}

@ApplicationScoped
class UserEntityRepository : PanacheRepositoryBase<UserEntity, String>

@ApplicationScoped
class UserOIDCMappingRepository : PanacheRepositoryBase<UserOIDCMapping, String> {
    fun getUserIdByUserSub(userSub: String): String? {
        return find("userSub = ?1", userSub).firstResult()?.userId
    }
}