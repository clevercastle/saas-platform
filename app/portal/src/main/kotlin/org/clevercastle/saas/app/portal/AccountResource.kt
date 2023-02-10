package org.clevercastle.saas.app.portal

import org.clevercastle.saas.core.model.account.UserEntity
import org.clevercastle.saas.core.model.account.UserEntityRepository
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("user")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AccountResource {
    @Inject
    private lateinit var userEntityRepository: UserEntityRepository

    @POST
    @Transactional
    fun registerUser(): UserEntity {
        val user = UserEntity().apply {
            default_tenant_id = "tenant_1"
            email = "abc"
        }
        println("??????????????//")
        println(user.created_at)
        println(user.updated_at)
        userEntityRepository.persist(user)
        return user
    }
}