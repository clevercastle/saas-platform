package org.clevercastle.saas.core.account

import org.clevercastle.saas.entity.core.account.AccountEntity
import org.clevercastle.saas.entity.core.account.WorkspaceEntity
import org.clevercastle.saas.model.core.account.Workspace
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers


@Mapper
interface WorkspaceConverter {
    companion object {
        val converter: WorkspaceConverter = Mappers.getMapper(WorkspaceConverter::class.java)
    }

    fun fromEntity(entity: WorkspaceEntity?): Workspace?
    fun fromEntityWithAccounts(workspaceEntity: WorkspaceEntity, accounts: List<AccountEntity>): Workspace {
        val workspace = fromEntity(workspaceEntity)
        workspace?.accounts = accounts.map { AccountConverter.converter.fromEntity(it)!! }.toMutableList()
        return workspace!!
    }
}