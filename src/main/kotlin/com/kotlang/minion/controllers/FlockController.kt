package com.kotlang.minion.controllers

import co.flock.model.User
import co.flock.model.event.*
import com.auth0.jwt.JWT
import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlang.minion.beans.ChatReceiveMessageExt
import com.kotlang.minion.beans.MessageAction
import com.kotlang.minion.flockHandler.FlockRouter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.web.bind.annotation.*


/**
 * Created by sainageswar on 08/01/17.
 */
@RestController
@RequestMapping("/flockapi")
class FlockController(@Autowired val flockRouter: FlockRouter) {
    private val log = LoggerFactory.getLogger(FlockController::class.java)

    @PostMapping
    fun handleFlockRequest(@RequestBody request: FlockEvent): ResponseEntity<String> {
        log.info("Request:: \n" + ObjectMapper().writerWithDefaultPrettyPrinter()
                .writeValueAsString(request))

        flockRouter.route(request)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("/teams")
    fun getTeamMates(@RequestParam("flockValidationToken") token: String): ResponseEntity<Array<User>> {
        val requestDetails = JWT.decode(token)?.claims
        val userId: String? = requestDetails?.get("userId")?.asString()
        when(userId) {
            null -> return ResponseEntity(HttpStatus.NOT_FOUND)
            else -> return ResponseEntity(flockRouter.getTeamMembers(userId), HttpStatus.OK)
        }
    }

    @Bean
    fun jacksonBuilder(): Jackson2ObjectMapperBuilder {
        val mapper = Jackson2ObjectMapperBuilder()
        mapper.indentOutput(true).mixIn(FlockEvent::class.java, FlockEventMixin::class.java)
        return mapper
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "name")
    @JsonSubTypes(
            JsonSubTypes.Type(value = AppInstall::class, name = "app.install"),
            JsonSubTypes.Type(value = AppUnInstall::class, name = "app.uninstall"),
            JsonSubTypes.Type(value = ChatReceiveMessageExt::class, name = "chat.receiveMessage"),
            JsonSubTypes.Type(value = FlockMLAction::class, name = "client.flockmlAction"),
            JsonSubTypes.Type(value = OpenAttachmentWidget::class, name = "client.openAttachmentWidget"),
            JsonSubTypes.Type(value = PressButton::class, name = "client.pressButton"),
            JsonSubTypes.Type(value = SlashCommand::class, name = "client.slashCommand"),
            JsonSubTypes.Type(value = WidgetAction::class, name = "client.widgetAction"),
            JsonSubTypes.Type(value = MessageAction::class, name = "client.messageAction"))
    interface FlockEventMixin
}