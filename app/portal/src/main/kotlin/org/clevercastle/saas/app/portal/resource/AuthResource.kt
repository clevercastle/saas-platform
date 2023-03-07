package org.clevercastle.saas.app.portal.resource

import org.apache.commons.lang3.StringUtils
import org.clevercastle.saas.app.common.vo.UserVO
import org.clevercastle.saas.app.portal.model.request.RegisterUserReq
import org.clevercastle.saas.core.account.AuthService
import org.clevercastle.saas.core.jwt.TokenHolder
import java.nio.charset.Charset
import java.util.*
import javax.annotation.security.PermitAll
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.Transactional
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("portal/auth")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AuthResource {
    @Inject
    private lateinit var authService: AuthService

    @PermitAll
    @POST
    @Path("register")
    @Transactional
    fun registerUser(@Valid req: RegisterUserReq): UserVO {
        val user = authService.register(req.email!!, req.password!!, req.name!!)
        return UserVO.fromUser(user)
    }

    @PermitAll
    @GET
    @Path("login")
    fun login(@HeaderParam("authorization") authorization: String): TokenHolder {
        if (StringUtils.isNotBlank(authorization)) {
            val auth = authorization.split(" ")
            if (auth.size == 2) {
                val userAndPassword = Base64.getDecoder().decode(auth[1]).toString(Charset.defaultCharset())
                        .split(":")
                if (userAndPassword.size == 2) {
                    return authService.login(userAndPassword[0], userAndPassword[1])
                }
            }
        }
        throw RuntimeException("")
    }
}