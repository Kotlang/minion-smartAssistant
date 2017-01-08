package com.kotlang.minion.flockHandler

import com.kotlang.minion.models.UserToken
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import repositories.UserTokenRepository
import kotlin.concurrent.thread

/**
 * Created by sainageswar on 08/01/17.
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
            }
        }
    }
}