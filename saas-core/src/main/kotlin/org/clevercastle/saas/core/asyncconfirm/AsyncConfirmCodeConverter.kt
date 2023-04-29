package org.clevercastle.saas.core.asyncconfirm

import org.clevercastle.saas.entity.core.asyncconfirm.AsyncConfirmCodeEntity
import org.clevercastle.saas.model.core.asyncconfirm.AsyncConfirmCode
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers


@Mapper
interface AsyncConfirmCodeConverter {
    companion object {
        val converter = Mappers.getMapper(AsyncConfirmCodeConverter::class.java)
    }

    fun convertToEntity(asyncConfirmCode: AsyncConfirmCode?): AsyncConfirmCodeEntity?
    fun fromEntity(entity: AsyncConfirmCodeEntity?): AsyncConfirmCode?
}