package org.clevercastle.saas.app.portal.resource

import org.clevercastle.saas.core.account.UserService
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("portal/account")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AccountResource {

    @Inject
    private lateinit var userService: UserService

    @GET
    fun getAccount(): Response {
        return Response.status(Response.Status.OK).entity(mapOf("hello" to "world")).build()
    }
}