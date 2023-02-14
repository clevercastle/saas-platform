package org.clevercastle.saas.app.portal.resource

import org.clevercastle.saas.app.common.vo.UserVO
import org.clevercastle.saas.app.portal.model.request.RegisterUserReq
import org.clevercastle.saas.core.account.UserService
import org.clevercastle.saas.core.jwt.TokenHolder
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.Transactional
import javax.validation.Valid
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
    private lateinit var userService: UserService

    @POST
    @Path("register")
    @Transactional
    fun registerUser(@Valid req: RegisterUserReq): UserVO {
        val user = userService.register(req.email!!, req.password!!, req.name!!)
        return UserVO.converter.fromUser(user)
    }

    @POST
    @Path("auth")
    @Transactional
    fun login(): TokenHolder {
        TODO()
    }

}