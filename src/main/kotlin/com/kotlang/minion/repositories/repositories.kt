package com.kotlang.minion.repositories

import com.kotlang.minion.models.FlockUser
import com.kotlang.minion.models.Team
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

/**
 * Created by sainageswar on 08/01/17.
 */
interface FlockUserRepository : JpaRepository<FlockUser, String> {
    @Query(" SELECT u FROM FlockUser u " +
            "WHERE u.team.teamId = (SELECT v.team.teamId FROM FlockUser v where v.userId=:userId)")
    fun findTeamMembers(@Param("userId") userId: String): List<FlockUser>
}

interface TeamRepository : JpaRepository<Team, String> {
}