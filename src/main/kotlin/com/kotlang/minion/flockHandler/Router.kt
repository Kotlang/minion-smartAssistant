package com.kotlang.minion.flockHandler

import co.flock.model.event.AppInstall
import co.flock.model.event.AppUnInstall
import co.flock.model.event.FlockEvent
import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlang.minion.beans.MessageAction
import com.kotlang.minion.models.UserToken
import com.kotlang.minion.repositories.UserTokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.concurrent.thread
import org.slf4j.LoggerFactory

/**
 * Created by sainageswar on 08/01/17.
 * Handles all flock requests
 */
@Component
class Router(@Autowired val userTokenRepository: UserTokenRepository) {
    private val log = LoggerFactory.getLogger(Router::class.java)

    fun route(request: FlockEvent): Unit {
        thread {
            when (request) {
                is AppInstall -> {
                    val userToken = UserToken(userId = request.userId, token = request.userToken)
                    log.info("Persisting user token")
                    userTokenRepository.save(userToken)
                }

                is AppUnInstall -> {
                    log.info("Removing user token")
                    userTokenRepository.delete(request.userId)
                }

                is MessageAction -> {
                    val userToken = userTokenRepository.findOne(request.userId)
                    val flockApiClient = UniFlockApiClient(userToken.token)

                    val messages = flockApiClient.fetchMessages(chat = request.chat,
                            uids = request.messageUids)
                    log.info("Messages are : \n" + ObjectMapper().writerWithDefaultPrettyPrinter()
                            .writeValueAsString(messages))
                }
            }
        }
    }
}