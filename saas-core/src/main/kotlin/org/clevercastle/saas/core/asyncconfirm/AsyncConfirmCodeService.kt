package org.clevercastle.saas.core.asyncconfirm

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.clevercastle.saas.entity.core.asyncconfirm.AsyncConfirmCodeEntityRepository
import org.clevercastle.saas.model.core.asyncconfirm.AsyncConfirmCode

@ApplicationScoped
class AsyncConfirmCodeService {

    private lateinit var asyncConfirmCodeEntityRepository: AsyncConfirmCodeEntityRepository

    constructor()

    @Inject
    constructor(asyncConfirmCodeEntityRepository: AsyncConfirmCodeEntityRepository) {
        this.asyncConfirmCodeEntityRepository = asyncConfirmCodeEntityRepository
    }

    fun createCode(asyncConfirmCode: AsyncConfirmCode) {
        val codeEntity = AsyncConfirmCodeConverter.converter.convertToEntity(asyncConfirmCode)
        asyncConfirmCodeEntityRepository.persist(codeEntity!!)
    }

    fun getByCode(code: String, codeType: AsyncConfirmCode.CodeType): AsyncConfirmCode? {
        return AsyncConfirmCodeConverter.converter.fromEntity(
            asyncConfirmCodeEntityRepository.getByCodeAndType(
                code,
                codeType
            )
        )
    }

}