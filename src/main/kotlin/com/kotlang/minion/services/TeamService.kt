package com.kotlang.minion.services

import com.kotlang.minion.flockHandler.UniFlockApiClient
import com.kotlang.minion.models.FlockUser
import com.kotlang.minion.models.Team
import com.kotlang.minion.repositories.FlockUserRepository
import com.kotlang.minion.repositories.TeamRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by sainageswar on 23/01/17.
 */
@Service
class TeamService (@Autowired val userRepository: FlockUserRepository,
                   @Autowired val teamRepository: TeamRepository) {

    fun createTeam(token: String) {
        val client = UniFlockApiClient(token)

        //get complete info of user
        val adminInfo = client.getUserInfo()
        adminInfo.token = token

        //create team
        val team = Team(teamId = adminInfo.teamId)
        teamRepository.save(team)

        //get other users of team
        val teamMembers = client.fetchMembers().toMutableList()
        teamMembers.add(adminInfo)

        teamMembers.forEach {
            val user = FlockUser(userId = it.id, firstName = it.firstName, token = it.token,
                    lastName = it.lastName, email = it.email, team = team)
            userRepository.save(user)
        }
    }

    fun deleteTeam(adminUserId: String) {
        val admin = userRepository.findOne(adminUserId)
        teamRepository.delete(admin.team)
    }

    fun getTeamMembers(userId: String) = userRepository.findTeamMembers(userId)
}