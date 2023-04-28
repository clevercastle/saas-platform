package org.clevercastle.saas.core.asyncconfirm

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.clevercastle.saas.core.model.asyncconfirm.AsyncConfirmCodeEntityRepository
import org.clevercastle.saas.core.model.asyncconfirm.CodeType

@ApplicationScoped
class AsyncConfirmCodeService {

    private lateinit var asyncConfirmCodeEntityRepository: AsyncConfirmCodeEntityRepository

    constructor()

    @Inject
    constructor(asyncConfirmCodeEntityRepository: AsyncConfirmCodeEntityRepository) {
        this.asyncConfirmCodeEntityRepository = asyncConfirmCodeEntityRepository
    }

    fun createCode(asyncConfirmCode: AsyncConfirmCode) {
        val codeEntity = AsyncConfirmCode.converter.convertToEntity(asyncConfirmCode)
        asyncConfirmCodeEntityRepository.persist(codeEntity!!)
    }

    fun getByCode(code: String, codeType: CodeType): AsyncConfirmCode? {
        return AsyncConfirmCode.converter.fromEntity(asyncConfirmCodeEntityRepository.getByCodeAndType(code, codeType))
    }

}