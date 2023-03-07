package org.clevercastle.saas.app.portal.resource

import io.quarkus.security.identity.SecurityIdentity
import org.clevercastle.saas.app.common.vo.UserVO
import org.clevercastle.saas.core.account.UserService
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("portal")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AccountResource {

    @Inject
    private lateinit var securityIdentity: SecurityIdentity

    @Inject
    private lateinit var userService: UserService

    @GET
    @Path("account")
    fun getAccount(): UserVO {
        return UserVO.converter.fromUser(userService.getUserByUserId(securityIdentity.principal.name)!!)
    }
}