package com.kotlang.minion.flockHandler

import co.flock.FlockApiClient
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

    fun route(request: Map<String, Any>): Unit {
        thread {
            when (request["name"] as String) {
                "app.install" -> {
                    val userToken = UserToken(userId = request["userId"] as String,
                            token = request["token"] as String)
                    log.info("Persisting user token")
                    userTokenRepository.save(userToken)
                }

                "app.uninstall" -> {
                    log.info("Removing user token")
                    userTokenRepository.delete(request["userId"] as String)
                }

                "client.messageAction" -> {
                    val userToken = userTokenRepository.findOne(request["userId"] as String)
                    val flockApiClient = FlockApiClient(userToken.token)

//                    flockApiClient.
                }
            }
        }
    }
}