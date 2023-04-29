package org.clevercastle.saas.model.core.asyncconfirm

import java.time.OffsetDateTime

/**
 * resource BO & model for email async confirm code
 */
class EmailAsyncConfirmCode {
    companion object {
        fun fromAsyncConfirmCode(asyncConfirmCode: AsyncConfirmCode?): EmailAsyncConfirmCode? {
            if (asyncConfirmCode == null) {
                return null
            }
            val code = EmailAsyncConfirmCode()
            code.email = asyncConfirmCode.bizKey
            code.username = asyncConfirmCode.bizDetail?.get("username")
            code.age = asyncConfirmCode.bizDetail?.get("age")?.toInt()

            code.code = asyncConfirmCode.code
            code.description = asyncConfirmCode.description
            code.expiredAt = asyncConfirmCode.expiredAt
            code.state = asyncConfirmCode.state
            code.tags = asyncConfirmCode.tags
            code.notes = asyncConfirmCode.notes
            return code
        }

        fun toAsyncConfirmCode(emailAsyncConfirmCode: EmailAsyncConfirmCode): AsyncConfirmCode {
            val code = AsyncConfirmCode()
            val bizDetail = mutableMapOf<String, String>()
            if (emailAsyncConfirmCode.username != null) {
                bizDetail["username"] = emailAsyncConfirmCode.username!!
            }
            if (emailAsyncConfirmCode.age != null) {
                bizDetail["age"] = emailAsyncConfirmCode.age!!.toString()
            }
            code.bizKey = emailAsyncConfirmCode.email
            code.bizDetail = bizDetail

            code.code = emailAsyncConfirmCode.code
            code.type = AsyncConfirmCode.CodeType.Email
            code.description = emailAsyncConfirmCode.description
            code.expiredAt = emailAsyncConfirmCode.expiredAt
            code.state = emailAsyncConfirmCode.state
            code.tags = emailAsyncConfirmCode.tags
            code.notes = emailAsyncConfirmCode.notes
            return code
        }
    }

    var email: String? = null

    var username: String? = null
    var age: Int? = null

    var code: String? = null
    val type: AsyncConfirmCode.CodeType = AsyncConfirmCode.CodeType.Email
    var description: String? = null
    var expiredAt: OffsetDateTime? = null
    var state: AsyncConfirmCode.State? = null
    var tags: List<String>? = null
    var notes: String? = null
}