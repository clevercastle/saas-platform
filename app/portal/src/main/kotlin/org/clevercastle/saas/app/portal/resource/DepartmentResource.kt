package org.clevercastle.saas.app.portal.resource

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.persistence.PersistenceException
import jakarta.transaction.RollbackException
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.clevercastle.saas.core.account.DepartmentService
import org.clevercastle.saas.core.account.PermissionService
import org.clevercastle.saas.core.account.UserService
import org.clevercastle.saas.core.account.WorkspaceService
import org.clevercastle.saas.core.internal.auth.SecurityService
import org.clevercastle.saas.core.internal.exception.HttpResponseException
import org.clevercastle.saas.core.internal.validation.EnumValidator
import org.clevercastle.saas.model.core.account.Department
import org.clevercastle.saas.model.core.account.DepartmentRole

@Path("portal/workspace/department")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class DepartmentResource {

    private lateinit var securityService: SecurityService
    private lateinit var userService: UserService
    private lateinit var workspaceService: WorkspaceService
    private lateinit var permissionService: PermissionService
    private lateinit var departmentService: DepartmentService

    constructor()

    @Inject
    constructor(
        securityService: SecurityService,
        userService: UserService,
        workspaceService: WorkspaceService,
        permissionService: PermissionService,
        departmentService: DepartmentService,
    ) {
        this.securityService = securityService
        this.userService = userService
        this.workspaceService = workspaceService
        this.permissionService = permissionService
        this.departmentService = departmentService
    }

    @GET
    @Path("")
    fun getDepartment(): List<Department> {
        val account = securityService.getAccount()
        return departmentService.listDepartment(account.workspaceId)
    }

    @POST
    @Path("")
    fun createDepartment(@Valid req: CreateDepartmentReq): Department {
        val account = securityService.getAccount()
        return departmentService.createDepartment(account.workspaceId, req.departmentName!!)
    }

    /**
     * Add one account to a department
     * only department adin can do this job
     */
    @PUT
    @Path("admin/add-account")
    fun addAccount(@Valid req: AddAccountToDepartmentReq): Response {
        // TODO: check the department is in the workspace and the operator is the admin of the department
        val account = securityService.getAccount()
        try {
            departmentService.addAccount(
                req.accountId!!,
                req.departmentId!!,
                req.role
            )
        } catch (e: RollbackException) {
            when (e.cause?.javaClass) {
                PersistenceException::class.java -> {
                    throw HttpResponseException(httpStatus = 400, message = "Account is already in the department.")
                }

                else -> {
                    throw HttpResponseException(httpStatus = 500, message = "")
                }
            }
        }
        return Response.ok().build()
    }

    /**
     * remove one account from a department
     * only department adin can do this job
     */
    @PUT
    @Path("admin/remove-account")
    fun removeAccount(
        @PathParam("workspaceId") workspaceId: String,
        @Valid req: RemoveAccountFromDepartmentReq
    ): Response {
        // TODO: check the department is in the workspace and the operator is the admin of the department
        val account = securityService.getAccount()
        if (account.workspaceId != workspaceId) {
            throw HttpResponseException(
                httpStatus = Response.Status.BAD_REQUEST.statusCode,
                message = "The workspace id in path is not correct"
            )
        }
        departmentService.removeAccount(
            req.accountId!!,
            req.departmentId!!,
        )
        return Response.ok().build()
    }
}

class CreateDepartmentReq {
    @field:NotEmpty(message = "Name is required")
    var departmentName: String? = null
}


class AddAccountToDepartmentReq {
    @field:NotEmpty(message = "AccountId is required")
    var accountId: String? = null

    @field:NotEmpty(message = "Department id is required")
    var departmentId: String? = null

    @field:EnumValidator(DepartmentRole::class, message = "Invalid role", method = "name")
    var role: DepartmentRole = DepartmentRole.NormalUser
}

class RemoveAccountFromDepartmentReq {
    @field:NotEmpty(message = "UserId is required")
    var accountId: String? = null

    @field:NotEmpty(message = "Department id is required")
    var departmentId: String? = null
}
