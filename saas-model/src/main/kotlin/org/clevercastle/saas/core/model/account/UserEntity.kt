package org.clevercastle.saas.core.model.account

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanionBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import org.clevercastle.saas.core.model.BaseEntity
import org.clevercastle.saas.core.model.EntityPrefix
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "users")
class UserEntity : BaseEntity() {
    companion object : PanacheCompanionBase<UserEntity, String>

    @Id
    var id: String = "${EntityPrefix.user}${UUID.randomUUID()}"
    lateinit var defaultTenantId: String
    lateinit var email: String
}

@ApplicationScoped
class UserEntityRepository : PanacheRepository<UserEntity>