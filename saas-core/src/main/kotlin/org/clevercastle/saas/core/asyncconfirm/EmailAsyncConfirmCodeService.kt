package org.clevercastle.saas.core.asyncconfirm

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.clevercastle.saas.base.TimeUtils
import org.clevercastle.saas.core.entity.asyncconfirm.AsyncConfirmCodeEntity
import org.clevercastle.saas.core.entity.asyncconfirm.CodeType

@ApplicationScoped
class EmailAsyncConfirmCodeService {
    private lateinit var asyncConfirmCodeService: AsyncConfirmCodeService

    constructor()

    @Inject
    constructor(asyncConfirmCodeService: AsyncConfirmCodeService) {
        this.asyncConfirmCodeService = asyncConfirmCodeService
    }

    @Transactional
    fun createCode(emailAsyncConfirmCode: EmailAsyncConfirmCode): EmailAsyncConfirmCode {
        emailAsyncConfirmCode.code = CodeUtil.generateCode(8)
        emailAsyncConfirmCode.expiredAt = TimeUtils.now().plusDays(1)
        emailAsyncConfirmCode.state = AsyncConfirmCodeEntity.State.Pending
        asyncConfirmCodeService.createCode(EmailAsyncConfirmCode.toAsyncConfirmCode(emailAsyncConfirmCode))
        return emailAsyncConfirmCode
    }

    fun getByCode(code: String): AsyncConfirmCode? {
        return asyncConfirmCodeService.getByCode(code, CodeType.Email)
    }
}