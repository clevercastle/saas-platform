package org.clevercastle.saas.core.account

import org.clevercastle.saas.entity.core.account.TeamEntity
import org.clevercastle.saas.model.core.account.Team
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers


@Mapper
interface TeamConverter {
    companion object {
        val converter: TeamConverter = Mappers.getMapper(TeamConverter::class.java)
    }

    fun fromEntity(entity: TeamEntity?): Team?
}