package com.kotlang.minion.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlang.minion.flockHandler.Router
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by sainageswar on 08/01/17.
 */
@RestController
@RequestMapping("/flockapi")
class FlockController (@Autowired val router: Router) {
    private val log = LoggerFactory.getLogger(FlockController::class.java)

    @PostMapping
    fun handleFlockRequest(@RequestBody request: Map<String, Any>): ResponseEntity<String> {
        log.info("Request:: \n" + ObjectMapper().writerWithDefaultPrettyPrinter()
                .writeValueAsString(request))

        router.route(request)
        return ResponseEntity(HttpStatus.OK)
    }
}