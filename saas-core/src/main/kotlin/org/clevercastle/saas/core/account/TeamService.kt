package org.clevercastle.saas.core.account

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.clevercastle.saas.entity.core.account.AccountTeamMappingEntityRepository
import org.clevercastle.saas.entity.core.account.TeamEntityRepository
import org.clevercastle.saas.model.core.account.Team

@ApplicationScoped
class TeamService {

    lateinit var teamEntityRepository: TeamEntityRepository
    lateinit var accountTeamMappingEntityRepository: AccountTeamMappingEntityRepository

    @Inject
    constructor(
        teamEntityRepository: TeamEntityRepository,
        accountTeamMappingEntityRepository: AccountTeamMappingEntityRepository
    ) {
        this.teamEntityRepository = teamEntityRepository
        this.accountTeamMappingEntityRepository = accountTeamMappingEntityRepository
    }


    fun listTeam(workspaceId: String): List<Team> {
        return this.teamEntityRepository.listTeam(workspaceId)
            .map { TeamConverter.converter.fromEntity(it)!! }
    }
}