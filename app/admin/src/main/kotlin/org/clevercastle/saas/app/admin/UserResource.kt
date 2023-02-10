package org.clevercastle.saas

import org.clevercastle.saas.app.admin.UserRepository
import org.clevercastle.saas.core.model.account.UserEntity
import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("user")
@ApplicationScoped
class ExampleResource {

    @Inject
    private lateinit var userRepository: UserRepository

    @ConfigProperty(name = "quarkus.datasource.jdbc.url")
    private lateinit var url: String

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getUser(@PathParam("id") id: String): Response {
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        // return Response.ok(UserEntity.findById(id)).build()
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    fun hello(): UserEntity {
        val user = UserEntity().apply {
            default_tenant_id = "1"
            email = "aaa"
        }
        userRepository.persist(user)
        return user
    }
}