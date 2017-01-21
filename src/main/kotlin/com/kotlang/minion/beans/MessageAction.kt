package com.kotlang.minion.beans

import co.flock.model.event.FlockEvent
import co.flock.model.message.Message

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
class MessageExt: Message("", "")

class ChatReceiveMessageExt(
        var message: MessageExt = MessageExt()
): FlockEvent()