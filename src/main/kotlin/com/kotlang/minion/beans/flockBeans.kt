package com.kotlang.minion.beans

import co.flock.model.User
import co.flock.model.event.FlockEvent
import co.flock.model.message.Message
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by sainageswar on 09/01/17.
 */
class MessageAction (
    var chat: String = "",
    var chatName: String = "",
    var userName: String = "",
    var messageUids: List<String> = listOf<String>()
): FlockEvent()

// Extending message to make it compatible with jackson
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class MessageExt: Message("", "")

class ChatReceiveMessageExt(
        var message: MessageExt = MessageExt()
): FlockEvent()

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class UserExt(
        var token: String? = null
): User()