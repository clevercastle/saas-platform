package org.clevercastle.saas.core.asyncconfirm

import org.clevercastle.saas.core.entity.asyncconfirm.AsyncConfirmCodeEntity
import org.clevercastle.saas.core.entity.asyncconfirm.CodeType
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import java.time.OffsetDateTime

class AsyncConfirmCode {
    companion object {
        val converter = Mappers.getMapper(AsyncConfirmCodeConverter::class.java)
    }

    //varchar(32)
    var code: String? = null

    var type: CodeType? = null
    var bizKey: String? = null

    var bizDetail: Map<String, String>? = null
    var description: String? = null
    var expiredAt: OffsetDateTime? = null

    var state: AsyncConfirmCodeEntity.State? = null

    var tags: List<String>? = null
    var notes: String? = null

}

@Mapper
interface AsyncConfirmCodeConverter {
    fun convertToEntity(asyncConfirmCode: AsyncConfirmCode?): AsyncConfirmCodeEntity?
    fun fromEntity(entity: AsyncConfirmCodeEntity?): AsyncConfirmCode?
}