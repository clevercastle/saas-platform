package org.clevercastle.saas.app.alpha.model

import org.clevercastle.saas.model.core.asyncconfirm.EmailAsyncConfirmCode
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import java.time.OffsetDateTime

data class CreateEmailAsyncConfirmCodeReq(
    val email: String? = null,
    val username: String? = null,
    val age: Int? = null,
    val code: String? = null,
    val description: String? = null,
    val expiredAt: OffsetDateTime? = null,
    val tags: List<String>? = null,
    val notes: String? = null
)

@Mapper
interface CreateEmailAsyncConfirmCodeReqConverter {
    companion object {
        val converter = Mappers.getMapper(CreateEmailAsyncConfirmCodeReqConverter::class.java)
    }

    fun toModel(req: CreateEmailAsyncConfirmCodeReq): EmailAsyncConfirmCode
}