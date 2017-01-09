package com.kotlang.minion.controllers

import co.flock.model.event.*
import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlang.minion.beans.MessageAction
import com.kotlang.minion.flockHandler.Router
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder


/**
 * Created by sainageswar on 08/01/17.
 */
@RestController
@RequestMapping("/flockapi")
class FlockController(@Autowired val router: Router) {
    private val log = LoggerFactory.getLogger(FlockController::class.java)

    @PostMapping
    fun handleFlockRequest(@RequestBody request: FlockEvent): ResponseEntity<String> {
        log.info("Request:: \n" + ObjectMapper().writerWithDefaultPrettyPrinter()
                .writeValueAsString(request))

        router.route(request)
        return ResponseEntity(HttpStatus.OK)
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
            JsonSubTypes.Type(value = ChatReceiveMessage::class, name = "chat.receiveMessage"),
            JsonSubTypes.Type(value = FlockMLAction::class, name = "client.flockmlAction"),
            JsonSubTypes.Type(value = OpenAttachmentWidget::class, name = "client.openAttachmentWidget"),
            JsonSubTypes.Type(value = PressButton::class, name = "client.pressButton"),
            JsonSubTypes.Type(value = SlashCommand::class, name = "client.slashCommand"),
            JsonSubTypes.Type(value = WidgetAction::class, name = "client.widgetAction"),
            JsonSubTypes.Type(value = MessageAction::class, name = "client.messageAction"))
    interface FlockEventMixin
}