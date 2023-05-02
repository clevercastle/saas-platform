package org.clevercastle.saas.app.portal.resource

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.RollbackException
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.clevercastle.saas.app.common.vo.AccountVO
import org.clevercastle.saas.app.common.vo.AccountVOConverter
import org.clevercastle.saas.core.account.AccountService
import org.clevercastle.saas.core.account.PermissionService
import org.clevercastle.saas.core.account.UserService
import org.clevercastle.saas.core.account.WorkspaceService
import org.clevercastle.saas.core.internal.auth.SecurityService
import org.clevercastle.saas.core.internal.exception.HttpResponseException
import org.clevercastle.saas.core.internal.validation.EnumValidator
import org.clevercastle.saas.model.core.account.AccountRole

@Path("portal/workspace")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AccountResource {

    private lateinit var userService: UserService
    private lateinit var accountService: AccountService
    private lateinit var workspaceService: WorkspaceService
    private lateinit var securityService: SecurityService
    private lateinit var permissionService: PermissionService

    constructor()

    @Inject
    constructor(
        userService: UserService,
        accountService: AccountService,
        workspaceService: WorkspaceService,
        securityService: SecurityService,
        permissionService: PermissionService
    ) {
        this.userService = userService
        this.accountService = accountService
        this.workspaceService = workspaceService
        this.securityService = securityService
        this.permissionService = permissionService
    }

    @GET
    @Path("account")
    fun getAccount(): AccountVO {
        val account = securityService.getAccount()
        val workspace = this.workspaceService.getWorkspace(account.workspaceId)
            ?: throw HttpResponseException(Response.Status.NOT_FOUND.statusCode, "Workspace not found")
        return AccountVOConverter.fromAccountAndWorkspace(account, workspace)
    }

    @PUT
    @Path("account/{accountId}")
    fun updateAccount(
        @PathParam("accountId") accountId: String,
        @Valid req: UpdateAccountReq
    ): Response {
        if (securityService.getAccount().id != accountId) {
            throw HttpResponseException(
                Response.Status.FORBIDDEN.statusCode,
                null,
                "Operator is not a member of the workspace"
            )
        }

        accountService.updateAccount(
            accountId,
            req.accountName,
            null
        )
        return Response.ok().build()
    }


    @POST
    @Path("/admin/account")
    fun createAccount(
        @Valid req: AdminCreateAccountReq
    ): Response {
        val workspaceId = securityService.getAccount().workspaceId
        permissionService.canAccessWorkspace(
            securityService.getUserId(),
            workspaceId,
            listOf(AccountRole.Owner, AccountRole.Admin)
        )
        // check req.userId is valid
        userService.getByUserId(req.userId)
            ?: throw HttpResponseException(httpStatus = 400, message = "Invalid user id")
        try {
            accountService.createAccount(req.userId, workspaceId, req.accountName, AccountRole.valueOf(req.accountRole))
        } catch (e: RollbackException) {
            if (e.cause is org.hibernate.exception.ConstraintViolationException) {
                throw HttpResponseException(httpStatus = 400, message = "User is already in workspace.")
            }
            throw HttpResponseException(httpStatus = 500, message = "Fail to create the account")
        }
        return Response.ok().build()
    }

    @PUT
    @Path("/admin/account/{accountId}")
    fun adminUpdateAccount(
        @PathParam("accountId") accountId: String,
        @Valid req: AdminUpdateAccountReq
    ): Response {
        val workspaceId = securityService.getAccount().workspaceId
        permissionService.canAccessWorkspace(
            securityService.getUserId(),
            workspaceId,
            listOf(AccountRole.Owner, AccountRole.Admin)
        )
        if (req.accountRole == null) {
            accountService.updateAccount(accountId, req.accountName, null)
        } else {
            accountService.updateAccount(
                accountId,
                req.accountName,
                AccountRole.valueOf(req.accountRole)
            )
        }
        return Response.ok().build()
    }


}

data class AdminCreateAccountReq(
    @field:NotEmpty(message = "UserId is required")
    val userId: String,
    @field:EnumValidator(AccountRole::class, message = "Invalid role", method = "name")
    val accountRole: String,
    val accountName: String
)


data class AdminUpdateAccountReq(
    @field:EnumValidator(AccountRole::class, message = "Invalid role", method = "name")
    val accountRole: String?,
    val accountName: String?
)

data class UpdateAccountReq(
    @field:NotEmpty(message = "Name is required")
    val accountName: String?
)