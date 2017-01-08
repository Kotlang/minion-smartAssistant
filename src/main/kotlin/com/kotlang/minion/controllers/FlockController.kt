package com.kotlang.minion.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by sainageswar on 08/01/17.
 */
@RestController
@RequestMapping("/flockapi")
class FlockController {
    private val log = LoggerFactory.getLogger(FlockController::class.java)

    @PostMapping
    fun handleFlockRequest(@RequestBody request: Map<String, Any>): Map<String, Any> {
        log.info("Request:: \n" + ObjectMapper().writerWithDefaultPrettyPrinter()
                .writeValueAsString(request))
        return mapOf("test" to "ok")
    }
}