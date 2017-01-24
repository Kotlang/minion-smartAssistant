package com.kotlang.minion.flockHandler

import co.flock.model.Group
import co.flock.model.message.Message
import com.mashape.unirest.http.Unirest
import com.kotlang.minion.beans.MessageExt
import com.kotlang.minion.beans.UserExt
import org.slf4j.LoggerFactory

class UniFlockApiClient(private val userToken: String) {
    private val log = LoggerFactory.getLogger(UniFlockApiClient::class.java)

    private val apiEndPoint = "https://api.flock.co/v1/"

    fun chatSendMessage(message: Message) =
            Unirest.post(apiEndPoint + "chat.sendMessage")
                    .header("accept", "application/json")
                    .queryString("token", userToken)
                    .body(message)
                    .asString().body

    fun getGroupInfo(groupId: String) =
            Unirest.post(apiEndPoint + "groups.getInfo")
                    .header("accept", "application/json")
                    .queryString("token", userToken)
                    .field("groupId", groupId)
                    .asObject(Group::class.java).body

    fun fetchMessages(chat: String, uids: List<String>) =
            Unirest.post(apiEndPoint + "chat.fetchMessages")
                    .queryString("token", userToken)
                    .body(mapOf("chat" to chat, "uids" to uids))
                    .asObject(Array<MessageExt>::class.java).body

    fun fetchMembers() =
            Unirest.get(apiEndPoint + "roster.listContacts")
                .queryString("token", userToken)
                .asObject(Array<UserExt>::class.java).body

    fun getUserInfo() =
            Unirest.get(apiEndPoint + "users.getInfo")
                .queryString("token", userToken)
                .asObject(UserExt::class.java).body

}