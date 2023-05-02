package org.clevercastle.saas.app.alpha.resource

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.clevercastle.saas.app.alpha.model.CreateEmailAsyncConfirmCodeReq
import org.clevercastle.saas.app.alpha.model.CreateEmailAsyncConfirmCodeReqConverter
import org.clevercastle.saas.core.asyncconfirm.EmailAsyncConfirmCodeService
import org.clevercastle.saas.model.core.asyncconfirm.EmailAsyncConfirmCode

@Path("alpha/code")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AlphaConfirmCodeController {

    @Inject
    private lateinit var emailAsyncConfirmCodeService: EmailAsyncConfirmCodeService

    /**
     * ignore the code&state from post body
     */
    @POST
    @Path("")
    fun createTask(emailAsyncConfirmCode: CreateEmailAsyncConfirmCodeReq): EmailAsyncConfirmCode {
        return emailAsyncConfirmCodeService.createCode(
            CreateEmailAsyncConfirmCodeReqConverter.converter.toModel(emailAsyncConfirmCode)
        )
    }

    @GET
    @Path("/{code}")
    fun getCode(@PathParam("code") code: String): EmailAsyncConfirmCode? {
        return EmailAsyncConfirmCode.fromAsyncConfirmCode(emailAsyncConfirmCodeService.getByCode(code))
    }
}