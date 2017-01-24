package com.kotlang.minion.controllers

import co.flock.model.User
import co.flock.model.event.*
import com.auth0.jwt.JWT
import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlang.minion.flockHandler.FlockRouter
import com.kotlang.minion.models.FlockUser
import com.kotlang.minion.services.TeamService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


/**
 * Created by sainageswar on 08/01/17.
 */
@RestController
@RequestMapping("/flockapi")
class FlockController(@Autowired val flockRouter: FlockRouter,
                      @Autowired val teamService: TeamService) {
    private val log = LoggerFactory.getLogger(FlockController::class.java)

    @PostMapping
    fun handleFlockRequest(@RequestBody request: FlockEvent): ResponseEntity<String> {
        log.info("Request:: \n" + ObjectMapper().writerWithDefaultPrettyPrinter()
                .writeValueAsString(request))

        flockRouter.route(request)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("{userToken}/team")
    fun getTeamForUser(@PathVariable userToken: String): ResponseEntity<List<FlockUser>> {
        val requestDetails = JWT.decode(userToken)?.claims
        val userId: String? = requestDetails?.get("userId")?.asString()
        when(userId) {
            null -> return ResponseEntity(HttpStatus.NOT_FOUND)
            else -> return ResponseEntity(teamService.getTeamMembers(userId), HttpStatus.OK)
        }
    }
}