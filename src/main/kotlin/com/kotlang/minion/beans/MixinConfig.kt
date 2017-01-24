package com.kotlang.minion.beans

import co.flock.model.User
import co.flock.model.event.*
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper

/**
 * Created by sainageswar on 24/01/17.
 */


fun configureMixins(objectMapper: ObjectMapper) {
    objectMapper.addMixIn(FlockEvent::class.java, FlockEventMixin::class.java)
    objectMapper.addMixIn(User::class.java, JsonIgnoreMixin::class.java)
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

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
interface JsonIgnoreMixin