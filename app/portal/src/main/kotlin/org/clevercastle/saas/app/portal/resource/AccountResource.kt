package org.clevercastle.saas.app.portal.resource

import org.apache.commons.lang3.StringUtils
import org.clevercastle.saas.app.common.vo.UserVO
import org.clevercastle.saas.app.portal.model.request.RegisterUserReq
import org.clevercastle.saas.core.account.UserService
import org.clevercastle.saas.core.jwt.TokenHolder
import java.nio.charset.Charset
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.Transactional
import javax.validation.Valid
import javax.ws.rs.*
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

    @GET
    @Path("auth")
    @Transactional
    fun login(@HeaderParam("authorization") authorization: String): TokenHolder {
        println(authorization)
        if (StringUtils.isNotBlank(authorization)) {
            val auth = authorization.split(" ")
            if (auth.size == 2) {
                val userAndPassword = Base64.getDecoder().decode(auth[1]).toString(Charset.defaultCharset())
                        .split(":")
                if (userAndPassword.size == 2) {
                    println(userAndPassword[0])
                    println(userAndPassword[1])
                    return userService.login(userAndPassword[0], userAndPassword[1])
                }
            }
        }
        throw RuntimeException("")
    }
}