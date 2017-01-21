package com.kotlang.minion.flockHandler

import co.flock.model.Group
import co.flock.model.User
import co.flock.model.message.Message
import com.mashape.unirest.http.Unirest
import com.fasterxml.jackson.core.JsonProcessingException
import com.kotlang.minion.beans.MessageExt
import com.mashape.unirest.http.ObjectMapper
import org.slf4j.LoggerFactory
import java.io.IOException

class UniFlockApiClient(private val userToken: String) {
    private val log = LoggerFactory.getLogger(UniFlockApiClient::class.java)

    init {
        Unirest.setObjectMapper(object : ObjectMapper {
            private val jacksonObjectMapper = com.fasterxml.jackson.databind.ObjectMapper()

            override fun <T> readValue(value: String, valueType: Class<T>): T {
                try {
                    return jacksonObjectMapper.readValue(value, valueType)
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }

            }

            override fun writeValue(value: Any): String {
                try {
                    return jacksonObjectMapper.writeValueAsString(value)
                } catch (e: JsonProcessingException) {
                    throw RuntimeException(e)
                }

            }
        })
    }

    private val apiEndPoint = "https://api.flock.co/v1/"

    fun chatSendMessage(message: Message): String {
        val response = Unirest.post(apiEndPoint + "chat.sendMessage")
                .header("accept", "application/json")
                .queryString("token", userToken)
                .body(message)
                .asString()
        return response.body
    }

    fun getGroupInfo(groupId: String): Group {
        val response = Unirest.post(apiEndPoint + "groups.getInfo")
                .header("accept", "application/json")
                .queryString("token", userToken)
                .field("groupId", groupId)
                .asObject(Group::class.java)
        return response.body
    }

    fun fetchMessages(chat: String, uids: List<String>): Array<MessageExt> {
        val response = Unirest.post(apiEndPoint + "chat.fetchMessages")
                .queryString("token", userToken)
                .body(mapOf("chat" to chat, "uids" to uids))
                .asObject(Array<MessageExt>::class.java)
        return response.body
    }

    fun fetchMembers(): Array<User> {
        val response = Unirest.get(apiEndPoint + "roster.listContacts")
                .queryString("token", userToken)
                .asObject(Array<User>::class.java)
        return response.body
    }
}