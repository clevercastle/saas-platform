package org.clevercastle.saas.app.portal.resource

import org.clevercastle.saas.app.common.auth.SecurityService
import org.clevercastle.saas.app.common.vo.TenantVO
import org.clevercastle.saas.app.common.vo.UserTenantVO
import org.clevercastle.saas.app.common.vo.UserVO
import org.clevercastle.saas.app.portal.model.request.CreateTenantReq
import org.clevercastle.saas.core.account.TenantService
import org.clevercastle.saas.core.account.UserService
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("portal")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AccountResource {
    @Inject
    private lateinit var securityService: SecurityService

    @Inject
    private lateinit var userService: UserService

    @Inject
    private lateinit var tenantService: TenantService

    @GET
    @Path("account")
    fun getAccount(): UserVO {
        return UserVO.fromUser(userService.getUserByUserId(securityService.getUserId())!!)
    }

    @POST
    @Path("tenant")
    fun createTenant(@Valid req: CreateTenantReq): TenantVO {
        val tenant = tenantService.createTenant(securityService.getUserId(), req.name!!, req.tenantUserName!!, false)
        return TenantVO.fromTenant(tenant)
    }

    @GET
    @Path("tenant")
    fun listUserTenants(): List<UserTenantVO> {
        val userId = securityService.getUserId()
        return tenantService.listUserTenant(userId).map { UserTenantVO.fromUserTenant(it) }
    }
}