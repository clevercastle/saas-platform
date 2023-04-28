package org.clevercastle.saas.app.portal.resource

import jakarta.annotation.security.PermitAll
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.apache.commons.lang3.StringUtils
import org.clevercastle.saas.app.common.vo.UserVO
import org.clevercastle.saas.app.portal.model.request.RegisterUserReq
import org.clevercastle.saas.core.account.AuthService
import org.clevercastle.saas.core.jwt.TokenHolder
import java.nio.charset.Charset
import java.util.*

@Path("portal/auth")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AuthResource {
    @Inject
    private lateinit var authService: AuthService

    constructor()

    constructor(authService: AuthService) {
        this.authService = authService
    }

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