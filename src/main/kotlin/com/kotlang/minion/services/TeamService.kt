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

        // get async responses
        val adminInfoResponse = client.getUserInfo()
        val teamMembersResponse = client.fetchMembers()

        //get complete info of user
        val adminInfo = adminInfoResponse.get().body
        adminInfo.token = token

        //create team
        val team = Team(teamId = adminInfo.teamId)
        teamRepository.save(team)

        //get other users of team
        val teamMembers = teamMembersResponse.get().body.toMutableList()
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

    /**
     * Returns chat members in chat
     * Chat members are our entities instead of flock entities
     */
    fun getChatMembers(chatId: String, userId: String): List<FlockUser> {
        val isGroup = chatId.startsWith("g:")

        val users: List<FlockUser>
        if (isGroup) {
            // get a token
            // we would be having token for only one of the member who installed app
            val teamMembers = userRepository.findTeamMembers(userId)
            val userToken = teamMembers.map { it.token }.filterNotNull().first()
            val client = UniFlockApiClient(userToken)

            val groupMembers = client.getGroupMembers(chatId)
            val userIds = groupMembers.map { it.id }
            users = userRepository.findAll(userIds)
        } else {
            users = listOf(userRepository.findOne(chatId))
        }

        return users
    }

    fun getTeamMembers(userId: String) = userRepository.findTeamMembers(userId)
}