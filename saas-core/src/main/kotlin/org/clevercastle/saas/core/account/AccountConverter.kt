package org.clevercastle.saas.core.account

import org.clevercastle.saas.entity.core.account.AccountEntity
import org.clevercastle.saas.model.core.account.Account
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface AccountConverter {
    companion object {
        val converter: AccountConverter = Mappers.getMapper(AccountConverter::class.java)
    }

    fun fromEntity(entity: AccountEntity?): Account?
}