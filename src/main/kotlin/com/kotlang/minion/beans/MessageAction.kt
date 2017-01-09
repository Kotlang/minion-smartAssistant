package com.kotlang.minion.beans

import co.flock.model.event.FlockEvent

/**
 * Created by sainageswar on 09/01/17.
 */
class MessageAction (
    var chat: String = "",
    var chatName: String = "",
    var userName: String = "",
    var messageUids: List<String> = listOf<String>()
): FlockEvent()