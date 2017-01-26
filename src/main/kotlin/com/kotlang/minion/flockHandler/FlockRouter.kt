package com.kotlang.minion.flockHandler

import co.flock.model.event.AppInstall
import co.flock.model.event.AppUnInstall
import co.flock.model.event.FlockEvent
import com.kotlang.minion.beans.ChatReceiveMessageExt
import com.kotlang.minion.services.TeamService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.concurrent.thread
import org.slf4j.LoggerFactory

/**
 * Created by sainageswar on 08/01/17.
 * Handles all flock requests
 */
@Component
class FlockRouter(@Autowired val teamService: TeamService) {
    private val log = LoggerFactory.getLogger(FlockRouter::class.java)

    fun route(request: FlockEvent): Unit {
        when (request) {
            is AppInstall -> {
                log.info("Creating team")
                teamService.createTeam(request.userToken)
            }

            is AppUnInstall -> {
                log.info("Removing user token")
                teamService.deleteTeam(request.userId)
            }

            is ChatReceiveMessageExt -> {
                val input = request.message.text
                val applicant = request.message.from

            }
        }
    }
}